package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenSettings.presentation.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}
