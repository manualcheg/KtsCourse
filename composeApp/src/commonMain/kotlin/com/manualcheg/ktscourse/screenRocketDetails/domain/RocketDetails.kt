package com.manualcheg.ktscourse.screenRocketDetails.domain

data class RocketDetails(
    val id: String,
    val name: String,
    val type: String?,
    val active: Boolean,
    val stages: Int?,
    val boosters: Int?,
    val costPerLaunch: Long?,
    val successRatePct: Int?,
    val firstFlight: String?,
    val country: String?,
    val company: String?,
    val wikipedia: String?,
    val description: String?,
    val imageUrl: String?,
    val height: Double?,
    val diameter: Double?,
    val mass: Double?,
    val payloadWeights: List<Pair<String, Double>>,
    val firstStage: String?,
    val secondStage: String?,
    val engines: String?,
    val landingLegs: String?,
    val flickrImages: List<String>,
    val isFavorite: Boolean = false
)
