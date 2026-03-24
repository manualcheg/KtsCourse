package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenProfile.data.ProfileRepositoryImpl
import com.manualcheg.ktscourse.screenProfile.domain.repository.ProfileRepository
import com.manualcheg.ktscourse.screenProfile.domain.usecase.GetUserDataUseCase
import com.manualcheg.ktscourse.screenProfile.domain.usecase.GetUserDataUseCaseImpl
import com.manualcheg.ktscourse.screenProfile.domain.usecase.LogoutUseCase
import com.manualcheg.ktscourse.screenProfile.domain.usecase.LogoutUseCaseImpl
import com.manualcheg.ktscourse.screenProfile.presentation.ViewModelProfileScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ViewModelProfileScreen)
    factoryOf(::LogoutUseCaseImpl) bind LogoutUseCase::class
    factoryOf(::GetUserDataUseCaseImpl) bind GetUserDataUseCase::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
}
