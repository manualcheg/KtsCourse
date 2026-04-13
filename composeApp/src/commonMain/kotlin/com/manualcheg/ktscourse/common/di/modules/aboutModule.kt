package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenAbout.presentation.ViewModelAboutScreen
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val aboutModule = module{
    viewModelOf(::ViewModelAboutScreen)

}
