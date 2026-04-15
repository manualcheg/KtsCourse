package com.manualcheg.ktscourse.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.manualcheg.ktscourse.domain.model.AppThemeType

// Цвета переносим из androidMain в commonMain
val SpaceBlack = Color(0xFF0B0D17)
val SpaceDarkBlue = Color(0xFF14172B)
val StarWhite = Color(0xFFFFFFFF)
val OxygenBlue = Color(0xFF4CB5F5)
val RocketOrange = Color(0xFFFF5722)
val DeepGray = Color(0xFF2C2C2C)

val NasaBlue = Color(0xFF0B3D91)
val MarsRed = Color(0xFFAD4328)
val LunarWhite = Color(0xFFF4F4F4)
val DeepSpaceText = Color(0xFF1A1A1A)
val SpaceShipGray = Color(0xFFD1D1D1)

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
fun AppTheme(
    appTheme: AppThemeType = AppThemeType.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (appTheme) {
        AppThemeType.LIGHT -> false
        AppThemeType.DARK -> true
        AppThemeType.SYSTEM -> isSystemInDarkThemeCustom()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}
