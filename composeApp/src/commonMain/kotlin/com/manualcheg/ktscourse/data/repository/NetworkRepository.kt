package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.Launch

interface NetworkRepository {
    suspend fun getAllLaunches(): Result<List<Launch>>
}