package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.database.LaunchDao
import com.manualcheg.ktscourse.data.database.toDomain
import com.manualcheg.ktscourse.data.database.toEntity
import com.manualcheg.ktscourse.screenMain.domain.models.Launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class LaunchRepository(
    private val networkRepository: NetworkRepository,
    private val launchDao: LaunchDao
) {
    suspend fun getPagedLaunchesFromDb(query: String, page: Int, limit: Int): List<Launch> {
        val offset = (page - 1) * limit
        val entities = if (query.isBlank()) {
            launchDao.getLaunchesPaged(limit, offset)
        } else {
            launchDao.searchLaunchesPaged(query, limit, offset)
        }
        return entities.map { it.toDomain() }
    }

    suspend fun fetchAndSaveLaunches(query: String, page: Int): Result<Boolean> = withContext(
        Dispatchers.IO
    ) {
        val result = networkRepository.getAllLaunches(query, page)
        return@withContext if (result.isSuccess) {
            val response = result.getOrThrow()
            val entities = response.docs.map { it.toEntity() }

            if (page == 1 && query.isBlank()) {
                launchDao.fetchAndSaveLaunchesTransaction(entities)
            } else {
                launchDao.insertLaunches(entities)
            }

            Result.success(response.hasNextPage)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}
