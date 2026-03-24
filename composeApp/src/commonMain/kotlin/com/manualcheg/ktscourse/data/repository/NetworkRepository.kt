package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto

interface NetworkRepository {
    suspend fun getAllLaunches(query: String, page: Int): Result<SpaceXResponseDto<Launch>>
}