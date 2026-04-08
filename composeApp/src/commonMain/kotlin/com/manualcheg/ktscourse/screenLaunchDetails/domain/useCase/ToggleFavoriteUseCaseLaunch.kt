package com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

interface ToggleFavoriteUseCaseLaunch {
    suspend operator fun invoke(launchDetails: LaunchDetails): Result<Boolean>
}
