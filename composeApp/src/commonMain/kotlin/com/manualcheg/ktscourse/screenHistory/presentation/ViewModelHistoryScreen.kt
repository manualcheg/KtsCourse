package com.manualcheg.ktscourse.screenHistory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.screenHistory.domain.usecase.GetHistoryUseCase
import com.manualcheg.ktscourse.common.util.toUserFriendlyMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelHistoryScreen(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistoryInfo()
    }

    fun loadHistoryInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getHistoryUseCase.execute()
                .onSuccess { history ->
                    _uiState.update { it.copy(historyInfo = history, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = error.toUserFriendlyMessage(), isLoading = false) }
                }
        }
    }
}
