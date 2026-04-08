package com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository

class GetLaunchDetailsUseCaseImpl(
    private val repository: LaunchRepository
) : GetLaunchDetailsUseCase {
    override suspend fun invoke(id: String): Result<LaunchDetails> {
        return repository.getLaunchById(id)
    }
}
