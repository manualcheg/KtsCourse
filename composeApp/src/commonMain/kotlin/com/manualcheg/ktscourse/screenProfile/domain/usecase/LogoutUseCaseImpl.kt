package com.manualcheg.ktscourse.screenProfile.domain.usecase

import com.manualcheg.ktscourse.screenProfile.domain.repository.ProfileRepository

class LogoutUseCaseImpl(
    private val profileRepository: ProfileRepository,
) : LogoutUseCase {
    override suspend operator fun invoke() {
        profileRepository.logout()
    }
}
