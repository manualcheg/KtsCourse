package com.manualcheg.ktscourse

import androidx.compose.ui.window.ComposeUIViewController
import com.manualcheg.ktscourse.common.App
import com.manualcheg.ktscourse.common.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
