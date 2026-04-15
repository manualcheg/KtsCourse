package com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase

import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult
import com.manualcheg.ktscourse.screenRocketLaunches.domain.RocketLaunchesRepository

class GetRocketLaunchesUseCaseImpl(
    private val repository: RocketLaunchesRepository
) : GetRocketLaunchesUseCase {
    override suspend operator fun invoke(
        rocketId: String,
        filterType: LaunchFilterType
    ): Result<LaunchesPageResult> {
        return repository.getLaunches(filterType).map { result ->
            result.copy(
                launches = result.launches.filter { it.rocketId == rocketId },
            )
        }
    }
}
