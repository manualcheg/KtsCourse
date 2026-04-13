package com.manualcheg.ktscourse.screenHistory.data

import com.manualcheg.ktscourse.data.mappers.toDomain
import com.manualcheg.ktscourse.data.repository.HistoryNetworkRepository
import com.manualcheg.ktscourse.screenHistory.domain.HistoryScreenRepository
import com.manualcheg.ktscourse.screenSettings.domain.History

class HistoryScreenRepositoryImpl(
    private val networkRepository: HistoryNetworkRepository
) : HistoryScreenRepository {

    override suspend fun getHistoryInfo(): Result<List<History>> {
        return networkRepository.getHistoryInfo().map { dtoList ->
            dtoList.toDomain()
        }
    }
}
