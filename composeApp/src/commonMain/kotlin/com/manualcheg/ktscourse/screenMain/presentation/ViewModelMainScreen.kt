package com.manualcheg.ktscourse.screenMain.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchesPageResult
import com.manualcheg.ktscourse.screenMain.domain.useCase.GetLaunchesUseCase
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
    private val getLaunchesUseCase: GetLaunchesUseCase
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
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(1000L)
            .onEach { query ->
                resetPagination()
                loadNextPage()
            }
            .launchIn(viewModelScope)
    }

    fun loadNextPage(isRefresh:Boolean = false) {
        val state = _uiState.value
        if (!isRefresh && (state.isLastPage || state.isNextPageLoading || state.isLoading || state.isRefreshing)) return

        val isFirstPage = isRefresh || state.launches.isEmpty()
        val pageToLoad = if (isFirstPage) 1 else currentPage + 1

        updateLoadingStatus(isFirstPage, isRefresh)

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            getLaunchesUseCase.execute(state.searchQuery, pageToLoad)
                .onSuccess { result ->
                    handleSuccess(result, isFirstPage, pageToLoad)
                }
                .onFailure { handleFailure(it) }
        }
    }

    private fun handleSuccess(result: LaunchesPageResult, isFirstPage: Boolean, pageLoaded: Int) {
        _uiState.update { state ->
            state.copy(
                launches = if (isFirstPage) result.launches else state.launches + result.launches,
                isLoading = false,
                isNextPageLoading = false,
                isRefreshing = false,
                isLastPage = result.isLastPage,
                error = null
            )
        }
        currentPage = pageLoaded
    }

    private fun handleFailure(error: Throwable) {
        _uiState.update {
            it.copy(
                error = error.message ?: "Unknown error",
                isLoading = false,
                isNextPageLoading = false,
                isRefreshing = false
            )
        }
    }

    private fun updateLoadingStatus(isFirstPage: Boolean, isRefresh: Boolean) {
        _uiState.update {
            when{
                isRefresh -> it.copy(isRefreshing = true, error = null)
                isFirstPage -> it.copy(isLoading = true, error = null)
                else -> it.copy(isNextPageLoading = true, error = null)
            }
            /*if (isFirstPage) it.copy(isLoading = isLoading)
            else it.copy(isNextPageLoading = isLoading)*/
        }
    }

    fun updateData() {
        loadNextPage(isRefresh = true)
        /*loadNextPage()
        _uiState.update { it.copy(isRefreshing = true) }
        currentPage = 1*/
    }

    fun onSearchQueryChange(newQuery: String) {
        _uiState.update {
            it.copy(searchQuery = newQuery)
        }
    }

    private fun resetPagination() {
        loadingJob?.cancel()
        currentPage = 1
        _uiState.update { it.copy(launches = emptyList(), isLastPage = false, error = null) }
    }
}
