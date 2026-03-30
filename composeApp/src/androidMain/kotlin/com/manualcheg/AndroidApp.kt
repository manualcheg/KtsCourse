package com.manualcheg

import android.app.Application
import com.manualcheg.ktscourse.common.di.initKoin
import com.manualcheg.ktscourse.data.database.appContext
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import ru.ok.tracer.crash.report.BuildConfig

class AndroidApp : Application() {
    override fun onCreate() {
        // Force IPv4 to avoid ConnectException on IPv6-enabled networks/emulators
        System.setProperty("java.net.preferIPv4Stack", "true")
        System.setProperty("java.net.preferIPv6Addresses", "false")

        appContext = this
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@AndroidApp)
        }

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }
        Napier.base(TracerAntilog())
    }
}
