package com.manualcheg.ktscourse.screenMain.presentation

import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.presentation.components.MainTab
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import com.manualcheg.ktscourse.screenRockets.presentation.RocketListUiState

enum class FavoriteType {
    Launches, Rockets
}

data class MainUiState(
    val launchesUiState: LaunchListUiState = LaunchListUiState(),
    val rocketsUiState: RocketListUiState = RocketListUiState(),
    val favoriteLaunches: List<Launch> = emptyList(),
    val favoriteRockets: List<Rocket> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val rocketFilterId: String? = null,
    val selectedTab: MainTab = MainTab.Launches,
    val selectedFavoriteType: FavoriteType = FavoriteType.Launches,
) {
    val itemsCount: Int = when (selectedTab) {
        MainTab.Launches -> launchesUiState.items.size
        MainTab.Rockets -> rocketsUiState.items.size
        MainTab.Favorites -> if (selectedFavoriteType == FavoriteType.Launches)
            favoriteLaunches.size
        else
            favoriteRockets.size
    }
    val showLoading = isLoading && itemsCount == 0
    val showErrorState = error != null && itemsCount == 0
    val showEmptyState = !isLoading && itemsCount == 0 && error == null

    val isRocketsFromCache: Boolean get() = rocketsUiState.isFromCache
    val isLaunchesFromCache: Boolean get() = launchesUiState.isFromCache
    val isNextPageLoading: Boolean get() = if (selectedTab == MainTab.Launches) launchesUiState.isNextPageLoading else rocketsUiState.isNextPageLoading
    val isLastPage: Boolean get() = if (selectedTab == MainTab.Launches) launchesUiState.isLastPage else rocketsUiState.isLastPage
}
