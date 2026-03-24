package com.manualcheg.ktscourse.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Composable
fun App() {
    AppNavHost()

    LaunchedEffect(Unit) {
        Napier.base(DebugAntilog())
    }
}
