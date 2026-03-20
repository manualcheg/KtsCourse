package com.manualcheg.ktscourse.screenMain.presentation

import com.manualcheg.ktscourse.screenMain.domain.models.Launch

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val launches: List<Launch>) : MainUiState()
    data class Error(val message: String) : MainUiState()
    object Empty : MainUiState()
}