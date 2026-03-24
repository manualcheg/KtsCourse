package com.manualcheg.ktscourse.screenLogin.domain.useCase

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.screenLogin.domain.model.UserCredentials

class LoginUseCaseImpl(private val userPreferencesRepository: UserPreferencesRepository) :
    LoginUseCase {
    override suspend fun invoke(credentials: UserCredentials): Boolean {
        return if (credentials.username == "user" && credentials.password == "password") {
            userPreferencesRepository.apply {
                saveLoginStatus(true)
                updateUsername(credentials.username)
            }
            true
        } else {
            false
        }
    }
}