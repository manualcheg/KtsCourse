package com.manualcheg.ktscourse.screenRocketDetails.domain

class GetRocketDetailsUseCaseImpl(
    private val repository: RocketDetailsRepository
) : GetRocketDetailsUseCase {
    override suspend fun invoke(rocketId: String): Result<RocketDetails> {
        return repository.getRocketDetails(rocketId)
    }
}
