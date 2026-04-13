package com.manualcheg.ktscourse.screenHistory.domain.usecase

import com.manualcheg.ktscourse.screenSettings.domain.History

interface GetHistoryUseCase {
    suspend fun execute(): Result<List<History>>
}
