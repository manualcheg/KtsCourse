package com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

interface GetLaunchDetailsUseCase {
    suspend operator fun invoke(id: String): Result<LaunchDetails>
}
