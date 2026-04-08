package com.manualcheg.ktscourse.presentation.theme

import DeepGray
import DeepSpaceText
import LunarWhite
import MarsRed
import NasaBlue
import OxygenBlue
import RocketOrange
import SpaceBlack
import SpaceDarkBlue
import SpaceShipGray
import StarWhite
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = StarWhite,
    onPrimary = SpaceBlack,
    secondary = OxygenBlue,
    onSecondary = StarWhite,
    tertiary = RocketOrange,
    onTertiary = StarWhite,
    background = SpaceBlack,
    onBackground = StarWhite,
    surface = SpaceDarkBlue,
    onSurface = StarWhite,
    surfaceVariant = DeepGray,
    onSurfaceVariant = StarWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = NasaBlue,
    onPrimary = Color.White,
    secondary = MarsRed,
    onSecondary = Color.White,
    background = LunarWhite,
    onBackground = DeepSpaceText,
    surface = Color.White,
    onSurface = DeepSpaceText,
    surfaceVariant = SpaceShipGray,
    onSurfaceVariant = DeepSpaceText,
    outline = Color.Gray,
)

@Composable
fun AppThemeMaterial(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
