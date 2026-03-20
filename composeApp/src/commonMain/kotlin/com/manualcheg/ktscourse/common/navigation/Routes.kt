package com.manualcheg.ktscourse.common.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Onboard : Screen()

    @Serializable
    object Login : Screen()

    @Serializable
    object Main : Screen()

    @Serializable
    object Profile : Screen()
}
