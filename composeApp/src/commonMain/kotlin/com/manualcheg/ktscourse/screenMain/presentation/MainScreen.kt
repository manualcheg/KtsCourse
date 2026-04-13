package com.manualcheg.ktscourse.screenMain.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manualcheg.ktscourse.common.components.EmptyState
import com.manualcheg.ktscourse.common.components.OfflineBadge
import com.manualcheg.ktscourse.screenMain.presentation.components.LaunchList
import com.manualcheg.ktscourse.screenMain.presentation.components.MainTab
import com.manualcheg.ktscourse.screenMain.presentation.components.MainTopAppBar
import com.manualcheg.ktscourse.screenMain.presentation.components.PagedListContainer
import com.manualcheg.ktscourse.screenRockets.presentation.RocketListUiState
import com.manualcheg.ktscourse.screenRockets.presentation.components.RocketList
import ktscourse.composeapp.generated.resources.Res
import ktscourse.composeapp.generated.resources.favorites_empty_launches
import ktscourse.composeapp.generated.resources.favorites_empty_rockets
import ktscourse.composeapp.generated.resources.favorites_tab_launches
import ktscourse.composeapp.generated.resources.favorites_tab_rockets
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    initialRocketId: String? = null,
    onProfileClick: () -> Unit = {},
    openLaunchDetails: (String) -> Unit,
    openRocketDetails: (String) -> Unit,
    viewModel: ViewModelMainScreen = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(initialRocketId) {
        if (initialRocketId != null) {
            viewModel.onRocketFilterChange(initialRocketId)
            viewModel.changeTab(MainTab.Launches)
        }
    }

    MainContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onRocketFilterChange = viewModel::onRocketFilterChange,
        onSettingsClick = onProfileClick,
        onRefresh = viewModel::updateData,
        onLoadNextPage = viewModel::loadNextPage,
        onRetry = viewModel::updateData,
        openLaunchDetails = openLaunchDetails,
        changeTab = viewModel::changeTab,
        changeFavoriteType = viewModel::changeFavoriteType,
        openRocketDetails = openRocketDetails,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    uiState: MainUiState,
    onSearchQueryChange: (String) -> Unit,
    onRocketFilterChange: (String?) -> Unit,
    onSettingsClick: () -> Unit,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    onRetry: () -> Unit,
    openLaunchDetails: (String) -> Unit,
    changeTab: (tab: MainTab) -> Unit,
    changeFavoriteType: (FavoriteType) -> Unit,
    openRocketDetails: (id: String) -> Unit,
) {
    Scaffold(
        topBar = {
            MainTopAppBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                rocketFilterId = uiState.rocketFilterId,
                onRocketFilterChange = onRocketFilterChange,
                onSettingsClick = onSettingsClick,
                showSearch = uiState.selectedTab != MainTab.Favorites,
            )
        },
        bottomBar = {
            NavigationBar {
                MainTab.entries.forEach { tab ->
                    val title = stringResource(tab.titleRes)
                    NavigationBarItem(
                        selected = uiState.selectedTab == tab,
                        onClick = { changeTab(tab) },
                        label = { Text(title) },
                        icon = { Icon(tab.icon, contentDescription = title) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when (uiState.selectedTab) {
                MainTab.Launches -> {
                    if (uiState.isLaunchesFromCache && !uiState.isLoading && uiState.error == null) {
                        OfflineBadge()
                    }
                    PagedListContainer(
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = onRefresh,
                        showLoading = uiState.showLoading,
                        showErrorState = uiState.showErrorState,
                        showEmptyState = uiState.showEmptyState,
                        error = uiState.error,
                        onRetry = onRetry,
                    ) {
                        LaunchList(
                            uiState = uiState.launchesUiState.copy(searchQuery = uiState.searchQuery),
                            loadNextPage = onLoadNextPage,
                            openLaunchDetails = openLaunchDetails,
                        )
                    }
                }

                MainTab.Rockets -> {
                    if (uiState.isRocketsFromCache && !uiState.isLoading && uiState.error == null) {
                        OfflineBadge()
                    }
                    PagedListContainer(
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = onRefresh,
                        showLoading = uiState.showLoading,
                        showErrorState = uiState.showErrorState,
                        showEmptyState = uiState.showEmptyState,
                        error = uiState.error,
                        onRetry = onRetry,
                    ) {
                        RocketList(
                            uiState = uiState.rocketsUiState.copy(searchQuery = uiState.searchQuery),
                            loadNextPage = onLoadNextPage,
                            openRocketDetails = openRocketDetails,
                        )
                    }
                }

                MainTab.Favorites -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        SingleChoiceSegmentedButtonRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        ) {
                            FavoriteType.entries.forEachIndexed { index, type ->
                                val label = when (type) {
                                    FavoriteType.Launches -> stringResource(Res.string.favorites_tab_launches)
                                    FavoriteType.Rockets -> stringResource(Res.string.favorites_tab_rockets)
                                }
                                SegmentedButton(
                                    selected = uiState.selectedFavoriteType == type,
                                    onClick = { changeFavoriteType(type) },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = FavoriteType.entries.size,
                                    ),
                                ) {
                                    Text(label)
                                }
                            }
                        }

                        if (uiState.selectedFavoriteType == FavoriteType.Launches) {
                            if (uiState.favoriteLaunches.isEmpty()) {
                                EmptyState(
                                    modifier = Modifier.fillMaxSize(),
                                    text = stringResource(Res.string.favorites_empty_launches),
                                )
                            } else {
                                LaunchList(
                                    uiState = LaunchListUiState(
                                        items = uiState.favoriteLaunches,
                                        isLastPage = true,
                                        searchQuery = "",
                                    ),
                                    loadNextPage = {},
                                    openLaunchDetails = openLaunchDetails,
                                )
                            }
                        } else {
                            if (uiState.favoriteRockets.isEmpty()) {
                                EmptyState(
                                    modifier = Modifier.fillMaxSize(),
                                    text = stringResource(Res.string.favorites_empty_rockets),
                                )
                            } else {
                                RocketList(
                                    uiState = RocketListUiState(
                                        items = uiState.favoriteRockets,
                                        isLastPage = true,
                                        searchQuery = "",
                                    ),
                                    loadNextPage = {},
                                    openRocketDetails = openRocketDetails,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainContent(
        uiState = MainUiState(isLoading = false),
        onSearchQueryChange = {},
        onRocketFilterChange = {},
        onSettingsClick = {},
        onRefresh = {},
        onLoadNextPage = {},
        onRetry = {},
        openLaunchDetails = { _ -> },
        changeTab = {},
        changeFavoriteType = {},
        openRocketDetails = { },
    )
}
