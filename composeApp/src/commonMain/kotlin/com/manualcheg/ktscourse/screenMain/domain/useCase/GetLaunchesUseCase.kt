package com.manualcheg.ktscourse.screenMain.domain.useCase

import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult

interface GetLaunchesUseCase {
    suspend fun execute(
        query: String,
        page: Int
    ): Result<LaunchesPageResult>
}
