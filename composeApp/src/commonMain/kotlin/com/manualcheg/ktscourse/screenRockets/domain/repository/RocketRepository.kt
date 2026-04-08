package com.manualcheg.ktscourse.screenRockets.domain.repository

import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket

interface RocketRepository {
    suspend fun getRockets(query: String, page: Int): Result<Boolean>
    suspend fun getPagedRockets(query: String, page: Int, limit: Int): List<Rocket>

    suspend fun toggleFavorite(rocket: RocketDetails): Result<Boolean>
}
