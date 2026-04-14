package com.manualcheg.ktscourse.screenHistory.presentation

import com.manualcheg.ktscourse.screenSettings.domain.History
import org.jetbrains.compose.resources.StringResource

data class HistoryUiState(
    val historyInfo: List<History>? = null,
    val isLoading: Boolean = false,
    val error: StringResource? = null
)
