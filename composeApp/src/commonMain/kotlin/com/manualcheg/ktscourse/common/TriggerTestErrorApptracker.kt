package com.manualcheg.ktscourse.common

import io.github.aakira.napier.Napier

/** Метод для тестирования отправки ошибок в Tracer */
fun triggerTestError(isFatal: Boolean = false) {
    if (isFatal) {
        throw RuntimeException("Fatal Test Error for Tracer")
    } else {
        val exception = IllegalStateException("Non-Fatal Test Error for Tracer")
        Napier.e(message = "Testing Tracer error reporting", throwable = exception)
    }
}
