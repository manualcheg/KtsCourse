package com.manualcheg.ktscourse.screenMain.domain.useCase

import com.manualcheg.ktscourse.common.util.executePagedRequest
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import io.github.aakira.napier.Napier

class GetLaunchesUseCaseImpl(private val launchRepository: LaunchRepository) : GetLaunchesUseCase {
    companion object {
        private const val PAGE_SIZE = 10
    }

    override suspend fun execute(
        query: String,
        page: Int
    ): Result<LaunchesPageResult> {
        return executePagedRequest(
            page = page,
            pageSize = PAGE_SIZE,
            fetchFromDb = { p, l -> launchRepository.getPagedLaunchesFromDb(query, p, l) },
            fetchFromNetwork = { p -> launchRepository.fetchAndSaveLaunches(query, p) },
            createResult = { items, hasNext, fromCache ->
                LaunchesPageResult(
                    launches = items,
                    isLastPage = !hasNext,
                    isFromCache = fromCache,
                )
            },
            onError = { e -> Napier.e("GetLaunchesUseCaseImpl error", e) },
        )
    }
}
