package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.RocketDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto

interface RocketNetworkRepository {
    suspend fun getRockets(query: String, page: Int): Result<SpaceXResponseDto<RocketDto>>
    suspend fun getRocket(id: String): Result<RocketDto>
    suspend fun getPastLaunches(): Result<List<LaunchDto>>
    suspend fun getUpcomingLaunches(): Result<List<LaunchDto>>
    suspend fun getLatestLaunch(): Result<LaunchDto>
    suspend fun getNextLaunch(): Result<LaunchDto>
}
