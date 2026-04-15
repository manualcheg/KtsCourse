package com.manualcheg.ktscourse.screenStatistics.domain.usecase

import com.manualcheg.ktscourse.screenStatistics.domain.Statistics
import kotlinx.coroutines.flow.Flow

interface GetStatisticsUseCase {
    operator fun invoke(): Flow<Result<Statistics>>
}
