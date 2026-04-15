package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.HistoryDto

interface HistoryNetworkRepository {
    suspend fun getHistoryInfo(): Result<List<HistoryDto>>
}
