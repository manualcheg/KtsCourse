package com.manualcheg.ktscourse.screenOnboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.screenOnboarding.domain.usecase.FirstStartUseCase
import com.manualcheg.ktscourse.screenOnboarding.domain.usecase.GetOnboardingItemsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class OnboardingEvent {
    data object NextPage : OnboardingEvent()

    data object MoveToLogin : OnboardingEvent()

    data object BackPage : OnboardingEvent()
}

class ViewModelOnboardingScreen(
    private val firstStartUseCase: FirstStartUseCase,
    getOnboardingItemsUseCase: GetOnboardingItemsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<OnboardingEvent>()
    val events: SharedFlow<OnboardingEvent> = _events.asSharedFlow()

    init {
        val items = getOnboardingItemsUseCase()
        _uiState.update {
            it.copy(items = items)
        }
    }

    fun onPageChanged(page: Int) {
        _uiState.update {
            it.copy(currentPage = page)
        }
    }

    fun onNextClick() {
        val state = _uiState.value
        if (state.isLastPage) {
            completeOnboarding()
        } else {
            sendEvent(OnboardingEvent.NextPage)
        }
    }

    fun onSkipClick() {
        completeOnboarding()
    }

    fun onBackClick() {
        val state = _uiState.value
        if (state.canGoBack) sendEvent(OnboardingEvent.BackPage)
    }

    private fun sendEvent(event: OnboardingEvent) {
        viewModelScope.launch { _events.emit(event) }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            _events.emit(OnboardingEvent.MoveToLogin)
            firstStartUseCase()
        }
    }
}
