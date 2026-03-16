package com.manualcheg.ktscourse.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.local_storage.DataStorePreferencesProvider
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.presentation.ui.LoginUiEvent
import com.manualcheg.ktscourse.presentation.ui.screens.uistates.LoginUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelLoginUiScreen(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<LoginUiEvent> = MutableSharedFlow()
    val events: SharedFlow<LoginUiEvent> = _events.asSharedFlow()

    fun onUsernameChanged(username: String) {
        _uiState.update {
            it.copy(
                username = username.trim()
            )
        }
        makeButtonLoginActive()
        makeTextInputInNormalState()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password.trim()
            )
        }
        makeButtonLoginActive()
        makeTextInputInNormalState()
    }

    fun makeButtonLoginActive() {
        _uiState.update {
            it.copy(
                isLoginButtonActive = it.username.isNotEmpty() && it.password.isNotEmpty(),
                error = false
            )
        }
    }

    fun checkCredentials() {
        viewModelScope.launch {
            if (_uiState.value.username == "user" && _uiState.value.password == "P@ssw0rd") {
                _events.emit(
                    LoginUiEvent.LoginSuccessEvent
                )
                userPreferencesRepository.apply {
                    setLoggedInVar(true)
                    updateUsername("user")
                }
            } else {
                _events.emit(LoginUiEvent.LoginErrorEvent)
                makeTextInputInErrorState()
            }
        }
    }

    fun makeTextInputInErrorState() {
        _uiState.update {
            it.copy(
                error = true
            )
        }
    }

    fun makeTextInputInNormalState() {
        _uiState.update {
            it.copy(
                error = false
            )
        }
    }
}
