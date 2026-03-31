package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase.GetLaunchDetailsUseCase
import com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase.GetLaunchDetailsUseCaseImpl
import com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase.ToggleFavoriteUseCase
import com.manualcheg.ktscourse.screenLaunchDetails.domain.useCase.ToggleFavoriteUseCaseImpl
import com.manualcheg.ktscourse.screenLaunchDetails.presentation.LaunchDetailsScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val launchDetailsModule =
    module {
        viewModelOf(::LaunchDetailsScreenViewModel)
        factoryOf(::GetLaunchDetailsUseCaseImpl) bind GetLaunchDetailsUseCase::class
        factoryOf(::ToggleFavoriteUseCaseImpl) bind ToggleFavoriteUseCase::class
    }
