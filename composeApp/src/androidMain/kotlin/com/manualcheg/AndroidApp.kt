package com.manualcheg

import android.app.Application
import com.manualcheg.ktscourse.common.di.initKoin
import com.manualcheg.ktscourse.data.database.appContext
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import ru.ok.tracer.CoreTracerConfiguration
import ru.ok.tracer.HasTracerConfiguration
import ru.ok.tracer.TracerConfiguration
import ru.ok.tracer.crash.report.BuildConfig
import ru.ok.tracer.crash.report.CrashFreeConfiguration
import ru.ok.tracer.crash.report.CrashReportConfiguration
import ru.ok.tracer.disk.usage.DiskUsageConfiguration
import ru.ok.tracer.heap.dumps.HeapDumpConfiguration
import ru.ok.tracer.profiler.sampling.SamplingProfilerConfiguration
import ru.ok.tracer.profiler.systrace.SystraceProfilerConfiguration

class AndroidApp : Application(), HasTracerConfiguration {
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

    override val tracerConfiguration: List<TracerConfiguration>
        get() = listOf(
            CoreTracerConfiguration.build {
                // tracer core options
            },
            CrashReportConfiguration.build {
                // crash collector options
            },
            CrashFreeConfiguration.build {
                // crash free counting options
            },
            HeapDumpConfiguration.build {
                // ООМ heap dumps collector options
            },
            DiskUsageConfiguration.build {
                // disk usage analyzer options
            },
            SystraceProfilerConfiguration.build {
                // production systrace profiler options
            },
            SamplingProfilerConfiguration.build {
                // sampling profiler options
            },
        )
}

