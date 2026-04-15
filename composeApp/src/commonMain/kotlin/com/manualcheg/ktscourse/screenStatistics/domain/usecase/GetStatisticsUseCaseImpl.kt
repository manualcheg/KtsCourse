package com.manualcheg.ktscourse.screenStatistics.domain.usecase

import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.common.util.NameHelper
import com.manualcheg.ktscourse.screenStatistics.domain.Statistics
import com.manualcheg.ktscourse.screenStatistics.domain.StatisticsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetStatisticsUseCaseImpl(
    private val statisticsRepository: StatisticsRepository,
    private val nameHelper: NameHelper,
) : GetStatisticsUseCase {
    override operator fun invoke(): Flow<Result<Statistics>> {
        return statisticsRepository.getStatisticsData().map { result ->
            result.map { launches ->
                val launchesByYear = launches
                    .filter { it.status != LaunchStatus.UPCOMING }
                    .mapNotNull { launch ->
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
                    mostUsedRocket = launches.groupBy { it.rocketId }
                        .maxByOrNull { it.value.size }
                        ?.key?.let { nameHelper.getRocketName(it) },
                    mostUsedLaunchpad = launches
                        .groupBy { it.launchpad }
                        .maxByOrNull { it.value.size }?.key
                        ?.let { nameHelper.getLaunchpadName(it) },
                )
            }
        }.catch { emit(Result.failure(it)) }
    }
}
