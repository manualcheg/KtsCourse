package com.manualcheg.ktscourse.screenLogin.data

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.screenLogin.domain.model.UserCredentials
import com.manualcheg.ktscourse.screenLogin.domain.repository.LoginRepository

class LoginRepositoryImpl(val userPreferencesRepository: UserPreferencesRepository) :
    LoginRepository {
    override suspend fun loginUser(credentials: UserCredentials) {
        userPreferencesRepository.apply {
            saveLoginStatus(true)
            updateUsername(credentials.username)
        }
    }
}
