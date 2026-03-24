package com.manualcheg.ktscourse.common.repository

import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userData: Flow<UserData>
    suspend fun updateUsername(name: String)
    suspend fun saveLoginStatus(isLoggedIn: Boolean)
    suspend fun updateFirstStartVar(isFirstStart: Boolean)
    suspend fun clearUserData()
}