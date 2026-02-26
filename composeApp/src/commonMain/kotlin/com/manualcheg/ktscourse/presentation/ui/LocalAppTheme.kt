package com.manualcheg.ktscourse.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var customAppThemeIsDark by mutableStateOf<Boolean?>(null)

expect object LocalAppTheme {
    val current: Boolean @Composable get
    @Composable
    infix fun provides(value: Boolean?): ProvidedValue<*>
}

@Composable
fun AppEnvironment(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppTheme provides customAppThemeIsDark,
    ) {
        key(customAppThemeIsDark) {
            content()
        }
    }
}