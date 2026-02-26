package com.manualcheg.ktscourse.data

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: Boolean = false
)