package com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase

import com.manualcheg.ktscourse.domain.model.Launch

interface GetRocketLaunchesUseCase {
    suspend operator fun invoke(rocketId: String): Result<List<Launch>>
}
