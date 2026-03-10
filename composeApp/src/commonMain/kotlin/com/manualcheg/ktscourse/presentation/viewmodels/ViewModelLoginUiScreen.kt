package com.manualcheg.ktscourse.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.presentation.ui.LoginUiEvent
import com.manualcheg.ktscourse.presentation.ui.screens.uistates.LoginUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelLoginUiScreen : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<LoginUiEvent> = MutableSharedFlow()
    val events = _events.asSharedFlow()

    fun onUsernameChanged(username: String) {
        _uiState.update {
            it.copy(
                username = username.trim()
            )
        }
        makeButtonLoginActive()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password.trim()
            )
        }
        makeButtonLoginActive()
    }

    fun makeButtonLoginActive() {
        if (uiState.value.username == "user" && uiState.value.password == "P@ssw0rd") {
            _uiState.update {
                it.copy(
                    isLoginButtonActive = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLoginButtonActive = false
                )
            }
        }
    }

    fun checkCredentials() {
        viewModelScope.launch {
            _events.emit(
                LoginUiEvent.LoginSuccessEvent
            )
        }
    }
}
