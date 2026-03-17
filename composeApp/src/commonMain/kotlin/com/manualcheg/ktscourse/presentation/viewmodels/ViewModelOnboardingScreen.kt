package com.manualcheg.ktscourse.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.presentation.ui.screens.onboarding.OnboardingItems
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class OnboardingEvent {
    object NextPage : OnboardingEvent()
    object MoveToLogin : OnboardingEvent()
    object BackPage : OnboardingEvent()
}

class ViewModelOnboardingScreen(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    private val _items = MutableStateFlow(OnboardingItems.getData())
    val items: StateFlow<List<OnboardingItems>> = _items.asStateFlow()

    private val _events = MutableSharedFlow<OnboardingEvent>()
    val events: SharedFlow<OnboardingEvent> = _events.asSharedFlow()

    fun onNextClick(currentPage: Int) {
        if (currentPage < _items.value.size - 1) {
            viewModelScope.launch { _events.emit(OnboardingEvent.NextPage) }
        } else {
            viewModelScope.launch {
                _events.emit(OnboardingEvent.MoveToLogin)
                userPreferencesRepository.updateFirstStartVar(false)
            }
        }
    }

    fun onSkipClick() {
        viewModelScope.launch {
            _events.emit(OnboardingEvent.MoveToLogin)
            userPreferencesRepository.updateFirstStartVar(false)
        }
    }

    fun onBackClick(currentPage: Int) {
        if (currentPage + 1 > 1) viewModelScope.launch {
            _events.emit(OnboardingEvent.BackPage)
        }
    }
}
