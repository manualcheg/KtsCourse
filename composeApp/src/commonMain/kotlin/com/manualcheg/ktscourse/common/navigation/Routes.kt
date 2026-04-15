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

    @Serializable
    data class LaunchDetails(val id: String) : Screen()

    @Serializable
    data class RocketDetails(val id: String) : Screen()

    @Serializable
    data class RocketLaunches(val rocketId: String, val rocketName: String) : Screen()

    @Serializable
    object Settings : Screen()

    @Serializable
    object AboutCompany : Screen()

    @Serializable
    object History : Screen()

    @Serializable
    object Statistic : Screen()
}
