package com.manualcheg.ktscourse.screenMain.data

import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class LaunchRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : LaunchRepository {
    override suspend fun getPagedLaunchesFromDb(
        query: String,
        page: Int,
        limit: Int
    ): List<Launch> {
        return databaseRepository.getPagedLaunchesFromDb(query, page, limit)
    }

    override suspend fun fetchAndSaveLaunches(query: String, page: Int): Result<Boolean> =
        withContext(
            Dispatchers.IO,
        ) {
            val result = networkRepository.getAllLaunches(query, page)
            return@withContext if (result.isSuccess) {
                val response = result.getOrThrow()
                val entities = response.docs.map { it.toEntity() }

                if (page == 1 && query.isBlank()) {
                    databaseRepository.fetchAndSaveLaunchesTransaction(entities)
                } else {
                    databaseRepository.insertLaunches(entities)
                }

                Result.success(response.hasNextPage)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
}