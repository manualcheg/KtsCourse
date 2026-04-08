package com.manualcheg.ktscourse.screenMain.presentation

import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.presentation.components.MainTab
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import com.manualcheg.ktscourse.screenRockets.presentation.RocketListUiState

enum class FavoriteType {
    Launches, Rockets
}

data class MainUiState(
    val launches: List<Launch> = emptyList(),
    val rocketsUiState: RocketListUiState = RocketListUiState(),
    val favoriteLaunches: List<Launch> = emptyList(),
    val favoriteRockets: List<Rocket> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isNextPageLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isLastPage: Boolean = false,
    val selectedTab: MainTab = MainTab.Launches,
    val selectedFavoriteType: FavoriteType = FavoriteType.Launches,
    val isLaunchesFromCache: Boolean = false,
) {
    val itemsCount: Int = when (selectedTab) {
        MainTab.Launches -> launches.size
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
}
