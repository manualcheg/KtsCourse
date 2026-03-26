package com.manualcheg.ktscourse.screenProfile.domain.usecase

import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface GetUserDataUseCase {
    fun execute(): Flow<UserData>
}
