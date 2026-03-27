package com.manualcheg.ktscourse.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {
    private object PreferenceKeys {
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val IS_FIRST_START = booleanPreferencesKey("is_first_start")
    }

    override val userData: Flow<UserData>
        get() = dataStore.data.map { prefs ->
            UserData(
                username = prefs[PreferenceKeys.USERNAME] ?: "",
                email = prefs[PreferenceKeys.EMAIL] ?: "",
                isLoggedIn = prefs[PreferenceKeys.IS_LOGGED_IN] ?: false,
                firstStart = prefs[PreferenceKeys.IS_FIRST_START] ?: true,
            )
        }

    override suspend fun updateUsername(name: String) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                this[PreferenceKeys.USERNAME] = name
            }
        }
    }

    override suspend fun saveLoginStatus(isLoggedIn: Boolean) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                this[PreferenceKeys.IS_LOGGED_IN] = isLoggedIn
            }
        }
    }

    override suspend fun updateFirstStartVar(isFirstStart: Boolean) {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                this[PreferenceKeys.IS_FIRST_START] = isFirstStart
            }
        }
    }

    override suspend fun clearUserData() {
        dataStore.updateData { prefs ->
            prefs.toMutablePreferences().apply {
                remove(PreferenceKeys.USERNAME)
                remove(PreferenceKeys.EMAIL)
                remove(PreferenceKeys.IS_LOGGED_IN)
            }
        }
    }
}
