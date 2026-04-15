package com.manualcheg.ktscourse.screenMain.presentation

import com.manualcheg.ktscourse.domain.model.Launch

data class LaunchListUiState(
    val items: List<Launch> = emptyList(),
    val isNextPageLoading: Boolean = false,
    val isLastPage: Boolean = false,
    val isFromCache: Boolean = false,
    val searchQuery: String = "",
    val lastQuery: String = "",
)
