package com.manualcheg.ktscourse.data.local_storage

import com.manualcheg.ktscourse.data.models.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val userData: Flow<UserData>
    suspend fun updateUsername(name: String)
    suspend fun updateEmail(email: String)
    suspend fun setLoggedInVar(isLoggedIn: Boolean)
    suspend fun updateFirstStartVar(isFirstStart: Boolean)
    suspend fun clearData()
}