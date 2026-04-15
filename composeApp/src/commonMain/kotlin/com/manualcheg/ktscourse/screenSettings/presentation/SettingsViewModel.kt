package com.manualcheg.ktscourse.screenSettings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.domain.model.AppThemeType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = repository.userData
        .map { userData ->
            SettingsUiState(
                appTheme = userData.appTheme,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState(),
        )

    fun setTheme(theme: AppThemeType) {
        viewModelScope.launch {
            repository.updateAppTheme(theme)
        }
    }
}
