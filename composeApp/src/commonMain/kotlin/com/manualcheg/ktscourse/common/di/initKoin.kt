package com.manualcheg.ktscourse.common.di

import com.manualcheg.ktscourse.common.di.modules.commonModule
import com.manualcheg.ktscourse.common.di.modules.loginModule
import com.manualcheg.ktscourse.common.di.modules.mainModule
import com.manualcheg.ktscourse.common.di.modules.onboardingModule
import com.manualcheg.ktscourse.common.di.modules.profileModule
import com.manualcheg.ktscourse.common.di.modules.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            platformModule,
            commonModule,
            onboardingModule,
            loginModule,
            mainModule,
            profileModule,
        )
    }
