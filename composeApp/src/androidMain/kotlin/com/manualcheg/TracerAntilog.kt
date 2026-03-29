package com.manualcheg

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import ru.ok.tracer.crash.report.TracerCrashReport

class TracerAntilog : Antilog() {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        // Отправляем в Tracer только ошибки и предупреждения
        if (priority == LogLevel.ERROR || priority == LogLevel.WARNING) {
            if (throwable != null) {
                TracerCrashReport.report(throwable)
            } else if (message != null) {
                val tagStr = if (tag != null) "[$tag] " else ""
                TracerCrashReport.report(RuntimeException("$tagStr$message"))
            }
        }
    }
}
