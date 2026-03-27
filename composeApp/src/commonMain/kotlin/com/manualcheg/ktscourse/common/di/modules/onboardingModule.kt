package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenOnboarding.domain.usecase.FirstStartUseCase
import com.manualcheg.ktscourse.screenOnboarding.domain.usecase.FirstStartUseCaseImpl
import com.manualcheg.ktscourse.screenOnboarding.domain.usecase.GetOnboardingItemsUseCase
import com.manualcheg.ktscourse.screenOnboarding.domain.usecase.GetOnboardingItemsUseCaseImpl
import com.manualcheg.ktscourse.screenOnboarding.presentation.ViewModelOnboardingScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val onboardingModule =
    module {
        viewModelOf(::ViewModelOnboardingScreen)
        factory { FirstStartUseCaseImpl(get()) } bind FirstStartUseCase::class
        factoryOf(::GetOnboardingItemsUseCaseImpl) bind GetOnboardingItemsUseCase::class
    }
