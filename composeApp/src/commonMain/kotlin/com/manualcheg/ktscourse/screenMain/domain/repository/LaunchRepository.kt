package com.manualcheg.ktscourse.screenMain.domain.repository

import com.manualcheg.ktscourse.screenMain.domain.model.Launch

interface LaunchRepository {
    suspend fun getPagedLaunchesFromDb(query: String, page: Int, limit: Int): List<Launch>
    suspend fun fetchAndSaveLaunches(query: String, page: Int): Result<Boolean>
}