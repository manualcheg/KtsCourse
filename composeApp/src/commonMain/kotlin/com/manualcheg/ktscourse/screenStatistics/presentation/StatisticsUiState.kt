package com.manualcheg.ktscourse.screenStatistics.presentation

import com.manualcheg.ktscourse.screenStatistics.domain.Statistics
import org.jetbrains.compose.resources.StringResource


data class StatisticsUiState(
    val statistics: Statistics? = null,
    val isLoading: Boolean = false,
    val error: StringResource? = null
)
