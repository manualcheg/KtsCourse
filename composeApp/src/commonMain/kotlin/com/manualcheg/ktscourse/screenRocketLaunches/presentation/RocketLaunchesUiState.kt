package com.manualcheg.ktscourse.screenRocketLaunches.presentation

import com.manualcheg.ktscourse.screenMain.presentation.LaunchListUiState
import org.jetbrains.compose.resources.StringResource

data class RocketLaunchesUiState(
    val launchesUiState: LaunchListUiState = LaunchListUiState(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: StringResource? = null,
) {
    private val itemsCount: Int = launchesUiState.items.size

    val showLoading = isLoading && itemsCount == 0 && !isRefreshing

    val showErrorState = error != null && itemsCount == 0

    val showEmptyState = !isLoading && !isRefreshing && itemsCount == 0 && error == null
}
