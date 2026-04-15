package com.manualcheg.ktscourse.screenMain.domain.useCase

import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult

interface GetLaunchesUseCase {
    suspend fun execute(
        query: String,
        rocketId: String?,
        filterType: LaunchFilterType,
        page: Int
    ): Result<LaunchesPageResult>
}
