package com.manualcheg.ktscourse.screenLogin.domain.repository

import com.manualcheg.ktscourse.screenLogin.domain.model.UserCredentials

interface LoginRepository {
    suspend fun loginUser(credentials: UserCredentials)
}
