package com.manualcheg.ktscourse.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.domain.model.AppThemeType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    repository: UserPreferencesRepository
) : ViewModel() {
    val theme: StateFlow<AppThemeType> = repository.userData
        .map { it.appTheme }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppThemeType.SYSTEM,
        )
}
