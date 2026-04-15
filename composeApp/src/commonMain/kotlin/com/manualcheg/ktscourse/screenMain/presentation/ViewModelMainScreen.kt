package com.manualcheg.ktscourse.screenMain.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.common.util.toUserFriendlyMessage
import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import com.manualcheg.ktscourse.screenFavorites.domain.repository.FavoritesRepository
import com.manualcheg.ktscourse.screenMain.domain.useCase.GetLaunchesUseCase
import com.manualcheg.ktscourse.screenMain.presentation.components.MainTab
import com.manualcheg.ktscourse.screenRockets.domain.usecase.GetRocketsUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelMainScreen(
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val getRocketsUseCase: GetRocketsUseCase,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var loadingJob: Job? = null

    init {
        search()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun search() {
        _uiState
            .map { Triple(it.searchQuery, it.rocketFilterId, it.selectedFilter) }
            .distinctUntilChanged()
            .debounce(1000L)
            .onEach {
                resetPagination()
                loadNextPage()
            }.launchIn(viewModelScope)
    }

    fun loadNextPage(isRefresh: Boolean = false) {
        val state = _uiState.value
        if (!isRefresh && (state.isLastPage || state.isNextPageLoading || state.isLoading || state.isRefreshing)) return

        val itemsEmpty = when (state.selectedTab) {
            MainTab.Launches -> state.launchesUiState.items.isEmpty()
            MainTab.Rockets -> state.rocketsUiState.items.isEmpty()
            MainTab.Favorites -> true
        }
        val isFirstPage = isRefresh || itemsEmpty
        val pageToLoad = if (isFirstPage) 1 else currentPage + 1

        updateLoadingStatus(isFirstPage, isRefresh)

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            when (state.selectedTab) {
                MainTab.Launches -> {
                    getLaunchesUseCase.execute(
                        state.searchQuery,
                        state.rocketFilterId,
                        state.selectedFilter,
                        pageToLoad,
                    )
                        .onSuccess { result ->
                            _uiState.update {
                                it.copy(
                                    launchesUiState = it.launchesUiState.copy(
                                        items = if (isFirstPage) {
                                            result.launches
                                        } else {
                                            (it.launchesUiState.items + result.launches).distinctBy { launch -> launch.id }
                                        },
                                        isFromCache = result.isFromCache,
                                        isLastPage = result.isLastPage,
                                        isNextPageLoading = false,
                                    ),
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = null,
                                )
                            }
                            currentPage = pageToLoad
                        }
                        .onFailure { handleFailure(it) }
                }

                MainTab.Rockets -> {
                    getRocketsUseCase.execute(state.searchQuery, pageToLoad)
                        .onSuccess { result ->
                            _uiState.update {
                                it.copy(
                                    rocketsUiState = it.rocketsUiState.copy(
                                        items = if (isFirstPage) result.rockets else it.rocketsUiState.items + result.rockets,
                                        isFromCache = result.isFromCache,
                                        isLastPage = !result.hasNextPage,
                                        isNextPageLoading = false,
                                    ),
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = null,
                                )
                            }
                            currentPage = pageToLoad
                        }
                        .onFailure { handleFailure(it) }
                }

                MainTab.Favorites -> {}
            }
        }
    }

    private fun handleFailure(error: Throwable) {
        if (error is kotlinx.coroutines.CancellationException) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    launchesUiState = it.launchesUiState.copy(isNextPageLoading = false),
                    rocketsUiState = it.rocketsUiState.copy(isNextPageLoading = false),
                )
            }
            return
        }

        Napier.e(
            message = "Failed to load data",
            throwable = error,
            tag = "ViewModelMainScreen",
        )
        _uiState.update {
            it.copy(
                error = error.toUserFriendlyMessage(),
                isLoading = false,
                isRefreshing = false,
                launchesUiState = it.launchesUiState.copy(
                    isFromCache = it.launchesUiState.items.isNotEmpty(),
                    isNextPageLoading = false,
                ),
                rocketsUiState = it.rocketsUiState.copy(
                    isFromCache = it.rocketsUiState.items.isNotEmpty(),
                    isNextPageLoading = false,
                ),
            )
        }
    }

    private fun updateLoadingStatus(
        isFirstPage: Boolean,
        isRefresh: Boolean,
    ) {
        _uiState.update {
            when {
                isRefresh -> it.copy(
                    isRefreshing = true,
                    error = null,
                    launchesUiState = it.launchesUiState.copy(isFromCache = false),
                    rocketsUiState = it.rocketsUiState.copy(isFromCache = false),
                )

                isFirstPage -> it.copy(isLoading = true, error = null)
                else -> it.copy(
                    launchesUiState = it.launchesUiState.copy(
                        isNextPageLoading = if (it.selectedTab == MainTab.Launches) true else it.launchesUiState.isNextPageLoading,
                    ),
                    rocketsUiState = it.rocketsUiState.copy(
                        isNextPageLoading = if (it.selectedTab == MainTab.Rockets) true else it.rocketsUiState.isNextPageLoading,
                    ),
                    error = null,
                )
            }
        }
    }

    fun updateData() {
        loadNextPage(isRefresh = true)
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update {
            it.copy(
                searchQuery = newQuery,
                rocketFilterId = null,
                launchesUiState = it.launchesUiState.copy(lastQuery = it.searchQuery),
            )
        }
    }

    fun onRocketFilterChange(rocketId: String?) {
        _uiState.update {
            it.copy(rocketFilterId = rocketId, searchQuery = "")
        }
    }

    fun onFilterSelected(filterType: LaunchFilterType) {
        _uiState.update {
            it.copy(selectedFilter = filterType, searchQuery = "")
        }
    }

    private fun resetPagination() {
        loadingJob?.cancel()
        currentPage = 1
        _uiState.update {
            it.copy(
                launchesUiState = it.launchesUiState.copy(
                    items = emptyList(),
                    isLastPage = false,
                    isNextPageLoading = false,
                ),
                rocketsUiState = it.rocketsUiState.copy(
                    items = emptyList(),
                    isLastPage = false,
                    isNextPageLoading = false,
                ),
                isLoading = false,
                isRefreshing = false,
                error = null,
            )
        }
    }

    fun changeTab(tab: MainTab) {
        _uiState.update { it.copy(selectedTab = tab, searchQuery = "") }
        if (tab == MainTab.Favorites) {
            loadFavorites()
        } else {
            resetPagination()
            loadNextPage()
        }
    }

    fun changeFavoriteType(type: FavoriteType) {
        _uiState.update { it.copy(selectedFavoriteType = type) }
        loadFavorites()
    }

    private var favoritesJob: Job? = null

    private fun loadFavorites() {
        favoritesJob?.cancel()
        favoritesJob = viewModelScope.launch {
            favoritesRepository.getAllFavoritesLaunches()
                .onEach { launches ->
                    _uiState.update { it.copy(favoriteLaunches = launches) }
                }.launchIn(this)

            favoritesRepository.getAllFavoritesRockets()
                .onEach { rockets ->
                    _uiState.update { it.copy(favoriteRockets = rockets) }
                }.launchIn(this)
        }
    }
}
