package com.manualcheg.ktscourse.screenRockets.presentation

import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket

data class RocketListUiState(
    val items: List<Rocket> = emptyList(),
    val isNextPageLoading: Boolean = false,
    val isLastPage: Boolean = false,
    val isFromCache: Boolean = false,
    val searchQuery: String = "",
)
