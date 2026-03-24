package com.manualcheg.ktscourse.screenLogin.domain.useCase

import com.manualcheg.ktscourse.screenLogin.domain.model.UserCredentials

interface LoginUseCase {
    suspend operator fun invoke(credentials: UserCredentials): Boolean
}