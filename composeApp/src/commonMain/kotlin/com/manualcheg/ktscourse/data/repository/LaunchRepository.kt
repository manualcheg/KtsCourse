package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.database.LaunchDao
import com.manualcheg.ktscourse.data.database.toDomain
import com.manualcheg.ktscourse.data.database.toEntity
import com.manualcheg.ktscourse.data.models.Launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LaunchRepository(
    private val networkRepository: NetworkRepository,
    private val launchDao: LaunchDao
) {
    fun getCachedLaunches(): Flow<List<Launch>> {
        return launchDao.getAllLaunches().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getPagedLaunchesFromDb(query: String, page: Int, limit: Int): List<Launch> {
        val offset = (page - 1) * limit
        val entities = if (query.isBlank()) {
            launchDao.getLaunchesPaged(limit, offset)
        } else {
            launchDao.searchLaunchesPaged(query, limit, offset)
        }
        return entities.map { it.toDomain() }
    }

    suspend fun fetchAndSaveLaunches(query: String, page: Int): Result<Unit> {
        val result = networkRepository.getAllLaunches(query, page)
        return if (result.isSuccess) {
            val response = result.getOrThrow()
            if (page == 1 && query.isBlank()) {
                launchDao.deleteAllLaunches()
            }
            launchDao.insertLaunches(response.docs.map { it.toEntity() })
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}
