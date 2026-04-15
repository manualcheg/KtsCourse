package com.manualcheg.ktscourse.screenMain.domain.repository

import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

interface LaunchRepository {
    suspend fun getPagedLaunchesFromDb(
        query: String,
        rocketId: String?,
        filterType: LaunchFilterType,
        page: Int,
        limit: Int
    ): List<Launch>

    suspend fun fetchAndSaveLaunches(
        query: String,
        rocketId: String?,
        filterType: LaunchFilterType,
        page: Int
    ): Result<Boolean>

    suspend fun getLaunchById(id: String): Result<LaunchDetails>
    suspend fun toggleFavorite(launch: LaunchDetails): Result<Boolean>
}
