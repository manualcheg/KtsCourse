package com.manualcheg.ktscourse.screenProfile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.database.DatabaseHolder
import com.manualcheg.ktscourse.data.datastore.DataStorePreferencesProvider
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelProfileScreen : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<ProfileUiEvent> = MutableSharedFlow()
    val events: SharedFlow<ProfileUiEvent> = _events.asSharedFlow()

    val userPreferencesRepository =
        UserPreferencesRepository(DataStorePreferencesProvider.datastore)
    val launchDao = DatabaseHolder.database.launchDao()


    init {
        viewModelScope.launch {
            userPreferencesRepository.userData.collect {
                updateUsername(it.username)
            }
        }
    }

    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(
                username = username
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            _events.emit(ProfileUiEvent.Logout)
            userPreferencesRepository.clearUserData()
            launchDao.deleteAllLaunches()
        }
    }
}