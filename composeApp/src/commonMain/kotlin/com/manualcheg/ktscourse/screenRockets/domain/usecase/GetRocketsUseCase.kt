package com.manualcheg.ktscourse.screenRockets.domain.usecase

import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket

data class RocketsPageResult(
    val rockets: List<Rocket>,
    val hasNextPage: Boolean,
    val isFromCache: Boolean
)

interface GetRocketsUseCase {
    suspend fun execute(query: String, page: Int): Result<RocketsPageResult>
}
