package com.manualcheg.ktscourse.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.manualcheg.ktscourse.common.navigation.AppNavHost
import com.manualcheg.ktscourse.common.presentation.AppViewModel
import com.manualcheg.ktscourse.presentation.theme.AppTheme
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val appViewModel: AppViewModel = koinViewModel()
    val appTheme by appViewModel.theme.collectAsState()

    AppTheme(appTheme = appTheme) {
        AppNavHost()
    }

    LaunchedEffect(Unit) {
        Napier.base(DebugAntilog())
    }
}
