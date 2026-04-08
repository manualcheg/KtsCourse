package com.manualcheg.ktscourse.screenRocketDetails.presentation

import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails

data class RocketDetailsUiState(
    val rocketDetails: RocketDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
