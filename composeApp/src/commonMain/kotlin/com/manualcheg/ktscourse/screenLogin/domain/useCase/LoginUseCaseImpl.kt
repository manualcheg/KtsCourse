package com.manualcheg.ktscourse.screenLogin.domain.useCase

import com.manualcheg.ktscourse.screenLogin.domain.model.UserCredentials
import com.manualcheg.ktscourse.screenLogin.domain.repository.LoginRepository

/*class LoginUseCaseImpl(private val userPreferencesRepository: UserPreferencesRepository) :
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
}*/
class LoginUseCaseImpl(private val loginRepository: LoginRepository) :
    LoginUseCase {
    override suspend fun invoke(credentials: UserCredentials): Boolean {
        return if (credentials.username == "user" && credentials.password == "password") {
            loginRepository.loginUser(credentials)
            true
        } else {
            false
        }
    }
}