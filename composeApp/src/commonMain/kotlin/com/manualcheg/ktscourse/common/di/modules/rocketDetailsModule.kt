package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenRocketDetails.data.RocketDetailsRepositoryImpl
import com.manualcheg.ktscourse.screenRocketDetails.domain.GetRocketDetailsUseCase
import com.manualcheg.ktscourse.screenRocketDetails.domain.GetRocketDetailsUseCaseImpl
import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetailsRepository
import com.manualcheg.ktscourse.screenRocketDetails.domain.ToggleFavoriteUseCaseRocket
import com.manualcheg.ktscourse.screenRocketDetails.domain.ToggleFavoriteUseCaseRocketImpl
import com.manualcheg.ktscourse.screenRocketDetails.presentation.RocketDetailsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val rocketDetailsModule =
    module {
        viewModelOf(::RocketDetailsViewModel)
        factoryOf(::GetRocketDetailsUseCaseImpl) bind GetRocketDetailsUseCase::class
        factoryOf(::ToggleFavoriteUseCaseRocketImpl) bind ToggleFavoriteUseCaseRocket::class
        singleOf(::RocketDetailsRepositoryImpl) bind RocketDetailsRepository::class
    }
