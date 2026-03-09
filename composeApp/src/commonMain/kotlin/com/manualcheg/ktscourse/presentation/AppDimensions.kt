package com.manualcheg.ktscourse.presentation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppDimensions(
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingStandard: Dp = 40.dp,
    val paddingLarge: Dp = 12.dp,
    val textSizeSmallest: TextUnit = 12.sp,
    val textSizeSmall: TextUnit = 18.sp,
    val textSizeLarge: TextUnit = 20.sp,
    val debounceDuration: Long = 500L,
    val shadowElevation: Dp = 2.dp,
    val spacerHeight: Dp = 4.dp,
    val imageSize: Dp = 64.dp,
    val successIndicatorSize: Dp = 8.dp,
)

val LocalDimensions = staticCompositionLocalOf { AppDimensions() }
