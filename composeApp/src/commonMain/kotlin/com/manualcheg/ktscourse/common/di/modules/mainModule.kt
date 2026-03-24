package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenMain.data.LaunchRepositoryImpl
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import com.manualcheg.ktscourse.screenMain.domain.useCase.GetLaunchesUseCase
import com.manualcheg.ktscourse.screenMain.domain.useCase.GetLaunchesUseCaseImpl
import com.manualcheg.ktscourse.screenMain.presentation.ViewModelMainScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::ViewModelMainScreen)
    factoryOf(::GetLaunchesUseCaseImpl) bind GetLaunchesUseCase::class
    singleOf(::LaunchRepositoryImpl) bind LaunchRepository::class
}
