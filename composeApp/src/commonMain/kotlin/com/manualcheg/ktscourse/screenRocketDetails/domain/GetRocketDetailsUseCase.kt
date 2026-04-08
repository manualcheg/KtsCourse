package com.manualcheg.ktscourse.screenRocketDetails.domain

interface GetRocketDetailsUseCase {
    suspend operator fun invoke(rocketId: String): Result<RocketDetails>
}
