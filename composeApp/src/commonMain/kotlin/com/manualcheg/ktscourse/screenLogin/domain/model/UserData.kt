package com.manualcheg.ktscourse.screenLogin.domain.model

data class UserData(
    val username: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val firstStart: Boolean = true
)
