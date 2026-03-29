package com.manualcheg.ktscourse.screenMain.presentation

import com.manualcheg.ktscourse.screenMain.domain.model.Launch

data class MainUiState(
    val launches: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isNextPageLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isLastPage: Boolean = false
) {
    val showLoading = isLoading && launches.isEmpty()
    val showErrorState = error != null && launches.isEmpty()
    val showEmptyState = !isLoading && launches.isEmpty() && error == null
}
