package com.manualcheg.ktscourse.screenRocketLaunches.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.common.util.toUserFriendlyMessage
import com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase.GetRocketLaunchesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RocketLaunchesViewModel(
    private val rocketId: String,
    private val getRocketLaunchesUseCase: GetRocketLaunchesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RocketLaunchesUiState())
    val uiState: StateFlow<RocketLaunchesUiState> = _uiState.asStateFlow()

    private var loadingJob: Job? = null

    init {
        loadNextPage()
    }

    fun loadNextPage(isRefresh: Boolean = false) {
        val state = _uiState.value
        if (!isRefresh && (state.isLoading || state.isRefreshing)) return

        updateLoadingStatus(isRefresh)

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            getRocketLaunchesUseCase(rocketId)
                .onSuccess { launches ->
                    _uiState.update {
                        it.copy(
                            launchesUiState = it.launchesUiState.copy(
                                items = launches,
                                isLastPage = true,
                                isNextPageLoading = false,
                            ),
                            isLoading = false,
                            isRefreshing = false,
                            error = null,
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.toUserFriendlyMessage(),
                            isLoading = false,
                            isRefreshing = false,
                        )
                    }
                }
        }
    }

    private fun updateLoadingStatus(isRefresh: Boolean) {
        _uiState.update {
            when {
                isRefresh -> it.copy(isRefreshing = true, error = null)
                else -> it.copy(isLoading = true, error = null)
            }
        }
    }

    fun refresh() {
        loadNextPage(isRefresh = true)
    }
}
