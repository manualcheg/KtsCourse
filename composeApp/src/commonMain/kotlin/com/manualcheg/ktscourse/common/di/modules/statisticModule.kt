package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenStatistics.data.StatisticsRepositoryImpl
import com.manualcheg.ktscourse.screenStatistics.domain.StatisticsRepository
import com.manualcheg.ktscourse.screenStatistics.domain.usecase.GetStatisticsUseCase
import com.manualcheg.ktscourse.screenStatistics.domain.usecase.GetStatisticsUseCaseImpl
import com.manualcheg.ktscourse.screenStatistics.presentation.ViewModelStatisticsScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val statisticsModule = module {
    viewModelOf(::ViewModelStatisticsScreen)
    factoryOf(::GetStatisticsUseCaseImpl) bind GetStatisticsUseCase::class
    singleOf(::StatisticsRepositoryImpl) bind StatisticsRepository::class
}
