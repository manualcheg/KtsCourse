package com.manualcheg.ktscourse.screenMain.domain.repository

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails
import com.manualcheg.ktscourse.screenMain.domain.model.Launch

interface LaunchRepository {
    suspend fun getPagedLaunchesFromDb(query: String, page: Int, limit: Int): List<Launch>
    suspend fun fetchAndSaveLaunches(query: String, page: Int): Result<Boolean>
    suspend fun getLaunchById(id: String): Result<LaunchDetails>
    suspend fun toggleFavorite(launch: LaunchDetails): Result<Boolean>
}
