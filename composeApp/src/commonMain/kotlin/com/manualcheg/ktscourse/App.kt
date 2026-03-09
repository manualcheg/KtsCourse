package com.manualcheg.ktscourse

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Composable
@Preview
fun App() {
    AppNavHost()
    Napier.base(DebugAntilog())
    Napier.v("app started")
}
