package com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase

import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult

interface GetRocketLaunchesUseCase {
    suspend operator fun invoke(
        rocketId: String,
        filterType: LaunchFilterType = LaunchFilterType.All
    ): Result<LaunchesPageResult>
}
