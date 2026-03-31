package com.manualcheg.ktscourse.common.di

import com.manualcheg.ktscourse.common.di.modules.commonModule
import com.manualcheg.ktscourse.common.di.modules.launchDetailsModule
import com.manualcheg.ktscourse.common.di.modules.loginModule
import com.manualcheg.ktscourse.common.di.modules.mainModule
import com.manualcheg.ktscourse.common.di.modules.networkModule
import com.manualcheg.ktscourse.common.di.modules.onboardingModule
import com.manualcheg.ktscourse.common.di.modules.platformModule
import com.manualcheg.ktscourse.common.di.modules.profileModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            platformModule,
            commonModule,
            networkModule,
            onboardingModule,
            loginModule,
            mainModule,
            profileModule,
            launchDetailsModule,
        )
    }
