package com.manualcheg.ktscourse.screenOnboarding.domain.usecase

import com.manualcheg.ktscourse.screenOnboarding.domain.model.OnboardingItem

interface GetOnboardingItemsUseCase {
    operator fun invoke(): List<OnboardingItem>
}
