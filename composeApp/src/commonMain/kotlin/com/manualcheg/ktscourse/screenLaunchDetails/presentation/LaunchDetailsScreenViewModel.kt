package com.manualcheg.ktscourse.screenLaunchDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase.GetLaunchDetailsUseCase
import com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase.ToggleFavoriteUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LaunchDetailsScreenViewModel(
    private val getLaunchDetailsUseCase: GetLaunchDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadLaunchDetails(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getLaunchDetailsUseCase(id)
                .onSuccess { details ->
                    _uiState.update { it.copy(launch = details, isLoading = false) }
                }
                .onFailure { error ->
                    Napier.e("Failed to load launch details for id: $id", error)
                    _uiState.update { it.copy(error = error.message, isLoading = false) }
                }
        }
    }

    fun toggleFavorite() {
        val currentLaunch = _uiState.value.launch
        viewModelScope.launch {
            toggleFavoriteUseCase(currentLaunch)
                .onSuccess { isFavorite ->
                    _uiState.update { it.copy(launch = currentLaunch.copy(isFavorite = isFavorite)) }
                }
                .onFailure { error ->
                    Napier.e("Failed to toggle favorite for launch id: ${currentLaunch.id}", error)
                }
        }
    }
}
