package com.manualcheg.ktscourse.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppDimensions(
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingStandard: Dp = 40.dp,
    val textSizeLarge: TextUnit = 20.sp
)

val LocalDimensions = staticCompositionLocalOf { AppDimensions() }
