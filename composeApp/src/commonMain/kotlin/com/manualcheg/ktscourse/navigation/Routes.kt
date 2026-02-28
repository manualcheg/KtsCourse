package com.manualcheg.ktscourse.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Onboard : Screen()

    @Serializable
    object Login : Screen()

    @Serializable
    object Main : Screen()
}
