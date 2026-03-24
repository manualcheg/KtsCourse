package com.manualcheg.ktscourse.screenOnboarding.presentation

import com.manualcheg.ktscourse.screenOnboarding.domain.model.OnboardingItem

data class OnboardingUiState(
    val items: List<OnboardingItem> = emptyList(),
    val currentPage: Int = 0
) {
    val isLastPage: Boolean = items.isNotEmpty() && currentPage == items.size - 1
    val canGoBack:Boolean = currentPage > 0
}