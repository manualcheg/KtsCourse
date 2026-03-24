package com.manualcheg.ktscourse.screenProfile.domain.usecase

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.data.repository.DatabaseRepository

class LogoutUseCaseImpl(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val databaseRepository: DatabaseRepository
) : LogoutUseCase {
    override suspend fun execute() {
        userPreferencesRepository.clearUserData()
        databaseRepository.deleteAllLaunches()
    }
}