package com.manualcheg.ktscourse.screenOnboarding.domain.usecase

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository

class FirstStartUseCaseImpl(private val userPreferencesRepository: UserPreferencesRepository) :
    FirstStartUseCase {
    override suspend fun invoke() {
        userPreferencesRepository.updateFirstStartVar(false)
    }
}
