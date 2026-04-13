package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenHistory.data.HistoryScreenRepositoryImpl
import com.manualcheg.ktscourse.screenHistory.domain.HistoryScreenRepository
import com.manualcheg.ktscourse.screenHistory.domain.usecase.GetHistoryUseCase
import com.manualcheg.ktscourse.screenHistory.domain.usecase.GetHistoryUseCaseImpl
import com.manualcheg.ktscourse.screenHistory.presentation.ViewModelHistoryScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val historyModule = module {
    viewModelOf(::ViewModelHistoryScreen)
    factoryOf(::GetHistoryUseCaseImpl) bind GetHistoryUseCase::class
    singleOf(::HistoryScreenRepositoryImpl) bind HistoryScreenRepository::class
}
