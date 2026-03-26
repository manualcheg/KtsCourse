package com.manualcheg.ktscourse.screenProfile.domain.repository

import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getData(): Flow<UserData>
    suspend fun logout()
}