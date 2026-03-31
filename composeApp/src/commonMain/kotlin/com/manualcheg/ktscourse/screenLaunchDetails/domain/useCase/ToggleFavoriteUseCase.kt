package com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

interface ToggleFavoriteUseCase {
    suspend operator fun invoke(launchDetails: LaunchDetails): Result<Boolean>
}
