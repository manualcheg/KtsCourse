package com.manualcheg.ktscourse.screenHistory.domain

import com.manualcheg.ktscourse.screenSettings.domain.History

interface HistoryScreenRepository {
    suspend fun getHistoryInfo(): Result<List<History>>
}
