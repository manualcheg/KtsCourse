package com.manualcheg.ktscourse.screenRocketDetails.domain

interface RocketDetailsRepository {
    suspend fun getRocketDetails(rocketId: String): Result<RocketDetails>

    suspend fun toggleFavorite(rocketDetails: RocketDetails): Result<Boolean>
}
