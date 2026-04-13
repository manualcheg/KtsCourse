package com.manualcheg.ktscourse.screenStatistics.domain

import com.manualcheg.ktscourse.domain.model.Launch
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {
    fun getStatisticsData(): Flow<Result<List<Launch>>>
}
