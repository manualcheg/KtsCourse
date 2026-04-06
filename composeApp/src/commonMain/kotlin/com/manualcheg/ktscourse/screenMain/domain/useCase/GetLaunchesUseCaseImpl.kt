package com.manualcheg.ktscourse.screenMain.domain.useCase

import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import io.github.aakira.napier.Napier

class GetLaunchesUseCaseImpl(private val launchRepository: LaunchRepository) : GetLaunchesUseCase {
    companion object {
        private const val PAGE_SIZE = 10
    }

    override suspend fun execute(
        query: String,
        page: Int
    ): Result<LaunchesPageResult> {
        return try {
            val networkResult = launchRepository.fetchAndSaveLaunches(query, page)
            val pagedData = launchRepository.getPagedLaunchesFromDb(query, page, PAGE_SIZE)
            val isFromCache = networkResult.isFailure

            val isLastPage = if (networkResult.isSuccess) {
                !networkResult.getOrThrow()
            } else {
                val hasMoreInDb =
                    launchRepository.getPagedLaunchesFromDb(query, page + 1, 1).isNotEmpty()
                pagedData.size < PAGE_SIZE || !hasMoreInDb
            }
            Result.success(
                LaunchesPageResult(
                    pagedData,
                    isLastPage,
                    isFromCache,
                ),
            )
        } catch (e: Exception) {
            Napier.e("GetLaunchesUseCaseImpl error", e)
            Result.failure(e)
        }
    }
}
