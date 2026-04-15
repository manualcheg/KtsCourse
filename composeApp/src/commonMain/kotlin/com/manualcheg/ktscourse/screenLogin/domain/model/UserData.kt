package com.manualcheg.ktscourse.screenLogin.domain.model

import com.manualcheg.ktscourse.domain.model.AppThemeType

data class UserData(
    val username: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val firstStart: Boolean = true,
    val appTheme: AppThemeType = AppThemeType.SYSTEM
)
