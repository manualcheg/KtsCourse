package com.manualcheg.ktscourse.screenRocketDetails.domain

class ToggleFavoriteUseCaseRocketImpl(private val repository: RocketDetailsRepository) :
    ToggleFavoriteUseCaseRocket {
    override suspend fun invoke(rocketDetails: RocketDetails): Result<Boolean> {
        return repository.toggleFavorite(rocketDetails)
    }
}
