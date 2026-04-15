package com.manualcheg.ktscourse.screenSettings.presentation

import com.manualcheg.ktscourse.domain.model.AppThemeType

data class SettingsUiState(
    val isNotificationEnabled: Boolean = false,
    val appTheme: AppThemeType = AppThemeType.SYSTEM
)
