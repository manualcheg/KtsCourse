package com.manualcheg.ktscourse.screenStatistics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.common.util.toUserFriendlyMessage
import com.manualcheg.ktscourse.screenStatistics.domain.usecase.GetStatisticsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelStatisticsScreen(
    private val getStatisticsUseCase: GetStatisticsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    fun retry() {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getStatisticsUseCase.invoke().collectLatest { result ->
                result
                    .onSuccess { stats ->
                    _uiState.update { it.copy(statistics = stats, isLoading = false) }
                }
                    .onFailure { error ->
                        _uiState.update { it.copy(error = error.toUserFriendlyMessage(), isLoading = false) }
                    }
            }
        }
    }
}
