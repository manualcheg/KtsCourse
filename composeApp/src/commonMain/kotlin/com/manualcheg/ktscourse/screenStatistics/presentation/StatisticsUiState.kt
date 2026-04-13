package com.manualcheg.ktscourse.screenStatistics.presentation

import com.manualcheg.ktscourse.screenStatistics.domain.Statistics


data class StatisticsUiState(
    val statistics: Statistics? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
