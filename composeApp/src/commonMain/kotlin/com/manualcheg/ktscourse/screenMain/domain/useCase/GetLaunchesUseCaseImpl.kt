package com.manualcheg.ktscourse.screenMain.domain.useCase

import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository

class GetLaunchesUseCaseImpl(private val launchRepository: LaunchRepository) : GetLaunchesUseCase {
    companion object {
        private const val PAGE_SIZE = 10
    }

    override suspend fun execute(
        query: String,
        page: Int
    ): Result<LaunchesPageResult> {
        return try {
            val networkResult = launchRepository.fetchAndSaveLaunches(query, page)
            val pagedData = launchRepository.getPagedLaunchesFromDb(query, page, PAGE_SIZE)

            val isLastPage = if (networkResult.isSuccess) {
                !networkResult.getOrThrow()
            } else {
                val hasMoreInDb =
                    launchRepository.getPagedLaunchesFromDb(query, page + 1, 1).isNotEmpty()
                pagedData.size < PAGE_SIZE || !hasMoreInDb
            }
            Result.success(LaunchesPageResult(pagedData, isLastPage))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
