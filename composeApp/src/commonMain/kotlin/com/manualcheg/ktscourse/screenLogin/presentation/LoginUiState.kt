package com.manualcheg.ktscourse.screenLogin.presentation

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: Boolean = false)