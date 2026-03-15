package com.manualcheg.ktscourse.presentation.ui.screens.uistates

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: Boolean = false)
