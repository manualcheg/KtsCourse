package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenRocketLaunches.data.RocketLaunchesRepositoryImpl
import com.manualcheg.ktscourse.screenRocketLaunches.domain.RocketLaunchesRepository
import com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase.GetRocketLaunchesUseCase
import com.manualcheg.ktscourse.screenRocketLaunches.domain.usecase.GetRocketLaunchesUseCaseImpl
import com.manualcheg.ktscourse.screenRocketLaunches.presentation.RocketLaunchesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val rocketLaunchesModule = module {
    viewModelOf(::RocketLaunchesViewModel)
    singleOf(::RocketLaunchesRepositoryImpl) bind RocketLaunchesRepository::class
    factoryOf(::GetRocketLaunchesUseCaseImpl) bind GetRocketLaunchesUseCase::class
}
