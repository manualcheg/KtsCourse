package com.manualcheg.ktscourse.screenLogin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.screenLogin.domain.model.UserCredentials
import com.manualcheg.ktscourse.screenLogin.domain.useCase.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelLoginUiScreen(private val loginUseCase: LoginUseCase) :
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
        val credentials = UserCredentials(_uiState.value.username, _uiState.value.password)
        viewModelScope.launch {
            val isSuccess =
                loginUseCase(credentials)
            if (isSuccess) {
                _events.emit(LoginUiEvent.LoginSuccessEvent)
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
