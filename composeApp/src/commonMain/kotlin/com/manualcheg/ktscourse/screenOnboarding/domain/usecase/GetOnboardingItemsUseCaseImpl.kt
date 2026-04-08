package com.manualcheg.ktscourse.screenOnboarding.domain.usecase

import com.manualcheg.ktscourse.screenOnboarding.domain.model.OnboardingItem

class GetOnboardingItemsUseCaseImpl : GetOnboardingItemsUseCase {
    override fun invoke(): List<OnboardingItem> = OnboardingItem.getData()
}
