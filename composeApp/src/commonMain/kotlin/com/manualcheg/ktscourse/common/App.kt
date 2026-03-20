package com.manualcheg.ktscourse.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Composable
@Preview
fun App() {
    AppNavHost()
    LaunchedEffect(Unit) {
        Napier.base(DebugAntilog())
    }
}
