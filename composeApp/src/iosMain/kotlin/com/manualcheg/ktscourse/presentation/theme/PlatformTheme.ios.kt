package com.manualcheg.ktscourse.presentation.theme

import androidx.compose.runtime.Composable
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.currentTraitCollection

@Composable
actual fun isSystemInDarkThemeCustom(): Boolean {
    val style = UITraitCollection.currentTraitCollection.userInterfaceStyle
    return style == UIUserInterfaceStyle.UIUserInterfaceStyleDark
}
