package com.manualcheg.ktscourse.screenRocketDetails.domain

interface ToggleFavoriteUseCaseRocket {
    suspend operator fun invoke(rocketDetails: RocketDetails): Result<Boolean>
}
