package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenLogin.data.LoginRepositoryImpl
import com.manualcheg.ktscourse.screenLogin.domain.repository.LoginRepository
import com.manualcheg.ktscourse.screenLogin.domain.useCase.LoginUseCase
import com.manualcheg.ktscourse.screenLogin.domain.useCase.LoginUseCaseImpl
import com.manualcheg.ktscourse.screenLogin.presentation.ViewModelLoginUiScreen
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::ViewModelLoginUiScreen)
    factory<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}
