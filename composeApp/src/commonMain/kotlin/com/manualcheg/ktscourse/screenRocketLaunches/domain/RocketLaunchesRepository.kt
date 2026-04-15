package com.manualcheg.ktscourse.screenRocketLaunches.domain

import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult

interface RocketLaunchesRepository {
    suspend fun getLaunches(filterType: LaunchFilterType): Result<LaunchesPageResult>
}
