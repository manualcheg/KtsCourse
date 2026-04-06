package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.LaunchDetailsDto
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto

interface NetworkRepository {
    suspend fun getLaunches(query: String, page: Int): Result<SpaceXResponseDto<LaunchDto>>

    suspend fun getLaunch(id: String): Result<LaunchDetailsDto>
}
