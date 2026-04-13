package com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase

import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenRocketLaunches.domain.RocketLaunchesRepository

class GetRocketLaunchesUseCaseImpl(
    private val repository: RocketLaunchesRepository
) : GetRocketLaunchesUseCase {
    override suspend operator fun invoke(rocketId: String): Result<List<Launch>> {
        return repository.getAllLaunches().map { launches ->
            launches.filter { it.rocketId == rocketId }
        }
    }
}
