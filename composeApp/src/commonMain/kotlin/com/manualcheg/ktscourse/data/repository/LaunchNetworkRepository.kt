package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.LaunchDetailsDto
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto
import com.manualcheg.ktscourse.domain.model.LaunchFilterType

interface LaunchNetworkRepository {
    suspend fun getLaunches(
        query: String,
        rocketId: String?,
        filterType: LaunchFilterType,
        page: Int
    ): Result<SpaceXResponseDto<LaunchDto>>

    suspend fun getAllLaunches(): Result<List<LaunchDto>>

    suspend fun getLaunch(id: String): Result<LaunchDetailsDto>
}
