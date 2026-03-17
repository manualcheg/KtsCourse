package com.manualcheg.ktscourse.presentation.ui.screens.uistates

import com.manualcheg.ktscourse.data.models.Launch

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val launches: List<Launch>) : MainUiState()
    data class Error(val message: String) : MainUiState()
    object Empty : MainUiState()
}
