package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.data.datastore.provideDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single { provideDataStore(androidContext()) }
}
