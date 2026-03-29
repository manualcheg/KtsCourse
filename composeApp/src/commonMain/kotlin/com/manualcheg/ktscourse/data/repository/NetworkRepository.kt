package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto

interface NetworkRepository {
    suspend fun getAllLaunches(query: String, page: Int): Result<SpaceXResponseDto<LaunchDto>>
}
