package com.manualcheg.ktscourse.common.util

import kotlinx.coroutines.CancellationException

suspend fun <T, R> executePagedRequest(
    page: Int,
    pageSize: Int,
    fetchFromDb: suspend (page: Int, size: Int) -> List<T>,
    fetchFromNetwork: suspend (page: Int) -> Result<Boolean>,
    createResult: (items: List<T>, hasNextPage: Boolean, isFromCache: Boolean) -> R,
    onError: (e: Throwable) -> Unit = {}
): Result<R> {
    return try {
        // Сначала проверяем, есть ли что-то в БД
        val cachedData = fetchFromDb(page, pageSize)

        // Пытаемся обновить данные из сети
        val networkResult = fetchFromNetwork(page)

        //  Получаем итоговые данные (обновленные или из кэша)
        val finalItems = if (networkResult.isSuccess) {
            fetchFromDb(page, pageSize)
        } else {
            cachedData
        }

        val isFromCache = networkResult.isFailure

        // Если данных пришло меньше, чем размер страницы, значит это точно последняя страница
        val hasNextPage = if (networkResult.isSuccess) {
            val serverHasNext = networkResult.getOrThrow()
            serverHasNext && finalItems.size >= pageSize
        } else {
            if (finalItems.isEmpty()) {
                false
            } else {
                // Проверяем, есть ли в БД данные дальше текущей страницы
                // Текущая страница закончилась на индексе (page * pageSize)
                fetchFromDb(1, page * pageSize + 1).size > page * pageSize
            }
        }

        Result.success(createResult(finalItems, hasNextPage, isFromCache))
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        onError(e)

        // Если всё совсем сломалось, пытаемся отдать хоть что-то из кэша
        try {
            val cachedData = fetchFromDb(page, pageSize)
            Result.success(createResult(cachedData, false, true))
        } catch (_: Exception) {
            Result.failure(e)
        }
    }
}
