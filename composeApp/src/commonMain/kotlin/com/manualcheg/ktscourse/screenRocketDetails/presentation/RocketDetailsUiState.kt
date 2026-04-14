package com.manualcheg.ktscourse.screenRocketDetails.presentation

import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails
import org.jetbrains.compose.resources.StringResource

data class RocketDetailsUiState(
    val rocketDetails: RocketDetails? = null,
    val isLoading: Boolean = false,
    val error: StringResource? = null,
)
