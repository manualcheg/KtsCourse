package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenMain.data.LaunchRepositoryImpl
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import com.manualcheg.ktscourse.screenMain.domain.useCase.GetLaunchesUseCase
import com.manualcheg.ktscourse.screenMain.domain.useCase.GetLaunchesUseCaseImpl
import com.manualcheg.ktscourse.screenMain.presentation.ViewModelMainScreen
import com.manualcheg.ktscourse.screenRockets.data.RocketRepositoryImpl
import com.manualcheg.ktscourse.screenRockets.domain.repository.RocketRepository
import com.manualcheg.ktscourse.screenRockets.domain.usecase.GetRocketsUseCase
import com.manualcheg.ktscourse.screenRockets.domain.usecase.GetRocketsUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule =
    module {
        viewModelOf(::ViewModelMainScreen)
        factoryOf(::GetLaunchesUseCaseImpl) bind GetLaunchesUseCase::class
        factoryOf(::GetRocketsUseCaseImpl) bind GetRocketsUseCase::class
        singleOf(::LaunchRepositoryImpl) bind LaunchRepository::class
        singleOf(::RocketRepositoryImpl) bind RocketRepository::class
    }
