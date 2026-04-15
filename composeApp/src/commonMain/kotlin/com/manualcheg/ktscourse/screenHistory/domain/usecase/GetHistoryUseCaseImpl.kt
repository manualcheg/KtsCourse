package com.manualcheg.ktscourse.screenHistory.domain.usecase

import com.manualcheg.ktscourse.screenHistory.domain.HistoryScreenRepository
import com.manualcheg.ktscourse.screenSettings.domain.History

class GetHistoryUseCaseImpl(
    private val repository: HistoryScreenRepository
) : GetHistoryUseCase {
    override suspend fun execute(): Result<List<History>> {
        return repository.getHistoryInfo().map { histories ->
            histories.sortedBy { it.eventDateUnix }
        }
    }
}
