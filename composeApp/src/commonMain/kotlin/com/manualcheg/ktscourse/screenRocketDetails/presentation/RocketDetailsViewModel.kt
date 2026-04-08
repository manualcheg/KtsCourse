package com.manualcheg.ktscourse.screenRocketDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.shareService.ShareServiceProvider
import com.manualcheg.ktscourse.screenRocketDetails.domain.GetRocketDetailsUseCase
import com.manualcheg.ktscourse.screenRocketDetails.domain.ToggleFavoriteUseCaseRocket
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RocketDetailsViewModel(
    private val shareServiceProvider: ShareServiceProvider,
    private val getRocketDetailsUseCase: GetRocketDetailsUseCase,
    private val toggleFavoriteUseCaseRocket: ToggleFavoriteUseCaseRocket,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RocketDetailsUiState())
    val uiState: StateFlow<RocketDetailsUiState> = _uiState.asStateFlow()

    fun loadRocketDetails(rocketId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getRocketDetailsUseCase(rocketId)
                .onSuccess { details ->
                    _uiState.update {
                        it.copy(
                            rocketDetails = details,
                            isLoading = false,
                        )
                    }
                }
                .onFailure { error ->
                    Napier.e("Failed to load rocket details for id: $rocketId", error)
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun toggleFavorite() {
        val rocketDetails = uiState.value.rocketDetails ?: return
        viewModelScope.launch {
            toggleFavoriteUseCaseRocket(rocketDetails)
                .onSuccess { isFavorite ->
                    _uiState.update { it.copy(rocketDetails = rocketDetails.copy(isFavorite = isFavorite)) }
                }
                .onFailure { error ->
                    Napier.e("Failed to toggle favorite for rocket id: ${rocketDetails.id}", error)
                }
        }
    }

    fun shareRocket() {
        val rocketDetails = _uiState.value.rocketDetails ?: return
        val shareText = """
            🚀 Rocket: ${rocketDetails.name}
            📊 Is active: ${rocketDetails.active}
            📏 Size: height = ${rocketDetails.height}m, diameter = ${rocketDetails.diameter}m
            ⚖️ Weight: ${rocketDetails.mass}kg
            🔗 More info: ${rocketDetails.wikipedia}
        """.trimIndent()
        try {
            shareServiceProvider.getShareService().share(shareText)
        } catch (e: Exception) {
            Napier.e("Failed to share rocket details", e)
        }
    }
}
