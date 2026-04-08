package com.manualcheg.ktscourse.screenRockets.domain.model

data class Rocket(
    val id: String = "",
    val name: String = "",
    val type: String? = null,
    val isActive: Boolean = false,
    val stages: Int? = null,
    val boosters: Int? = null,
    val costPerLaunch: Long? = 0L,
    val successRatePct: Int? = null,
    val firstFlight: String? = "",
    val country: String? = "",
    val company: String? = null,
    val wikipedia: String? = null,
    val description: String? = "",
    val imageUrl: String? = "",
    val height: Double? = 0.0,
    val diameter: Double? = 0.0,
    val mass: Double? = null,
    val isFavorite: Boolean = false,
)
