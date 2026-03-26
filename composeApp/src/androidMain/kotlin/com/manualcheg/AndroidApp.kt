package com.manualcheg

import android.app.Application
import com.manualcheg.ktscourse.common.di.initKoin
import com.manualcheg.ktscourse.data.database.appContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        initKoin {
            androidLogger()
            androidContext(this@AndroidApp)
        }
    }
}
