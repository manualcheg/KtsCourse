package com.manualcheg.ktscourse.screenRocketLaunches.data

import com.manualcheg.ktscourse.data.mappers.toDomain
import com.manualcheg.ktscourse.data.mappers.toEntity
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenRocketLaunches.domain.RocketLaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RocketLaunchesRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) :
    RocketLaunchesRepository {
    override suspend fun getAllLaunches(): Result<List<Launch>> {
        return withContext(Dispatchers.IO) {
            val networkResult = networkRepository.getAllLaunches()
            if (networkResult.isSuccess) {
                val entities = networkResult.getOrThrow().map { it.toEntity() }

                databaseRepository.insertLaunches(entities)
                Result.success(entities.map { it.toDomain() })
            } else {
                // Пытаемся взять всё из БД (без фильтрации здесь)
                val cached = databaseRepository.getPagedLaunchesFromDb("", null, 1, 1000)
                if (cached.isNotEmpty()) {
                    Result.success(cached)
                } else {
                    Result.failure(networkResult.exceptionOrNull() ?: Exception("Unknown error"))
                }
            }
        }
    }
}
