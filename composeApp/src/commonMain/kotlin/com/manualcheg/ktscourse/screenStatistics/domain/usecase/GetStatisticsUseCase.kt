package com.manualcheg.ktscourse.screenStatistics.domain.usecase

import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.screenStatistics.domain.Statistics
import com.manualcheg.ktscourse.screenStatistics.domain.StatisticsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) {
    operator fun invoke(): Flow<Result<Statistics>> {
        return statisticsRepository.getStatisticsData().map { result ->
            result.map { launches ->
                val launchesByYear = launches
                    .filter { it.status != LaunchStatus.UPCOMING }
                    .mapNotNull { launch ->
                        // Извлекаем год из строки
                        launch.launchDate.split("-").firstOrNull()?.toIntOrNull()
                    }
                    .groupBy { it }
                    .mapValues { it.value.size }
                    .toList()
                    .sortedBy { it.first }
                    .toMap()

                Statistics(
                    totalLaunches = launches.size,
                    successLaunches = launches.count { it.status == LaunchStatus.SUCCESS },
                    failedLaunches = launches.count { it.status == LaunchStatus.FAILURE },
                    upcomingLaunches = launches.count { it.status == LaunchStatus.UPCOMING },
                    launchesByYear = launchesByYear,
                )
            }
        }
    }
}
