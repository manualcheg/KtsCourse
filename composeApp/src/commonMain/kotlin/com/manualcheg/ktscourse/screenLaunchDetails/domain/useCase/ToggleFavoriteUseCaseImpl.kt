package com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository

class ToggleFavoriteUseCaseImpl(
    private val repository: LaunchRepository
) : ToggleFavoriteUseCase {
    override suspend fun invoke(launchDetails: LaunchDetails): Result<Boolean> {
        return repository.toggleFavorite(launchDetails)
    }
}
