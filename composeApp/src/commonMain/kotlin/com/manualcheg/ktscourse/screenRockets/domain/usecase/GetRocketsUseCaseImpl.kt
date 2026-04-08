package com.manualcheg.ktscourse.screenRockets.domain.usecase

import com.manualcheg.ktscourse.common.util.executePagedRequest
import com.manualcheg.ktscourse.screenRockets.domain.repository.RocketRepository
import io.github.aakira.napier.Napier

class GetRocketsUseCaseImpl(
    private val rocketRepository: RocketRepository
) : GetRocketsUseCase {
    override suspend fun execute(query: String, page: Int): Result<RocketsPageResult> {
        return executePagedRequest(
            page = page,
            pageSize = 10,
            fetchFromDb = { p, s -> rocketRepository.getPagedRockets(query, p, s) },
            fetchFromNetwork = { p -> rocketRepository.getRockets(query, p) },
            createResult = { items, hasNext, fromCache ->
                RocketsPageResult(
                    rockets = items,
                    hasNextPage = hasNext,
                    isFromCache = fromCache,
                )
            },
            onError = { e -> Napier.e("GetRocketsUseCaseImpl error", e) },
        )
    }
}
