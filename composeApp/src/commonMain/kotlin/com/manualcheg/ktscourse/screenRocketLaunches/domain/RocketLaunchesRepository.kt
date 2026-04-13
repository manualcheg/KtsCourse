package com.manualcheg.ktscourse.screenRocketLaunches.domain

import com.manualcheg.ktscourse.domain.model.Launch

interface RocketLaunchesRepository {
    suspend fun getAllLaunches(): Result<List<Launch>>
}
