package com.manualcheg.ktscourse.screenProfile.domain.usecase

import com.manualcheg.ktscourse.screenProfile.domain.repository.ProfileRepository

class LogoutUseCaseImpl(
    private val profileRepository: ProfileRepository,
) : LogoutUseCase {
    override suspend fun execute() {
        profileRepository.logout()
    }
}