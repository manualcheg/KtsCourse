package com.manualcheg.ktscourse.presentation.ui.screens.UIStates

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: Boolean = false
)
