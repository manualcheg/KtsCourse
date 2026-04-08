package com.manualcheg.ktscourse.screenLaunchDetails.presentation

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

data class LaunchDetailsUiState(
    val launch: LaunchDetails? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
