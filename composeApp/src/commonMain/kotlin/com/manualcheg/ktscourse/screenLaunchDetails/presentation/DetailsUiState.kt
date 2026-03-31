package com.manualcheg.ktscourse.screenLaunchDetails.presentation

import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

data class DetailsUiState(
    val launch: LaunchDetails = LaunchDetails(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
