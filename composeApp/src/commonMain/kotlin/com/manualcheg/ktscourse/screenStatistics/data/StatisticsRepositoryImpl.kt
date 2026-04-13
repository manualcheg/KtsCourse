package com.manualcheg.ktscourse.screenStatistics.data

import com.manualcheg.ktscourse.data.mappers.toEntity
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.data.toDomain
import com.manualcheg.ktscourse.screenStatistics.domain.StatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StatisticsRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : StatisticsRepository {

    override fun getStatisticsData(): Flow<Result<List<Launch>>> = flow {
        val networkResult = networkRepository.getAllLaunches()
        if (networkResult.isSuccess) {
            val launches = networkResult.getOrThrow()
            databaseRepository.insertLaunches(launches.map { it.toEntity() })
            emit(Result.success(launches.map { it.toDomain() }))
        } else {
            emit(Result.failure(networkResult.exceptionOrNull() ?: Exception("Unknown error")))
        }
    }.flowOn(Dispatchers.IO)
}
