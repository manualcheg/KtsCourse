package com.manualcheg.ktscourse.screenRocketLaunches.data

import com.manualcheg.ktscourse.data.mappers.toDomain
import com.manualcheg.ktscourse.data.mappers.toEntity
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult
import com.manualcheg.ktscourse.screenRocketLaunches.domain.RocketLaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RocketLaunchesRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) :
    RocketLaunchesRepository {
    override suspend fun getLaunches(filterType: LaunchFilterType): Result<LaunchesPageResult> {
        return withContext(Dispatchers.IO) {
            val networkResult = when (filterType) {
                LaunchFilterType.All -> networkRepository.getAllLaunches()
                LaunchFilterType.Past -> networkRepository.getPastLaunches()
                LaunchFilterType.Upcoming -> networkRepository.getUpcomingLaunches()
                LaunchFilterType.Latest -> networkRepository.getLatestLaunch().map { listOf(it) }
                LaunchFilterType.Next -> networkRepository.getNextLaunch().map { listOf(it) }
            }
            if (networkResult.isSuccess) {
                val docs = networkResult.getOrThrow()
                val entities = docs.map { it.toEntity() }

                databaseRepository.insertLaunches(entities)
                Result.success(
                    LaunchesPageResult(
                        launches = entities.map { it.toDomain() },
                        isLastPage = true,
                        isFromCache = false,
                    ),
                )
            } else {
                // из БД без фильтрации
                val cached = databaseRepository.getPagedLaunchesFromDb(
                    query = "",
                    rocketId = null,
                    filterType = LaunchFilterType.All,
                    page = 1,
                    limit = 1000,
                )
                if (cached.isNotEmpty()) {
                    Result.success(
                        LaunchesPageResult(
                            launches = cached,
                            isLastPage = true,
                            isFromCache = true,
                        ),
                    )
                } else {
                    Result.failure(networkResult.exceptionOrNull() ?: Exception("Unknown error"))
                }
            }
        }
    }
}
