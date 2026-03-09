package com.manualcheg.ktscourse.data.models

data class Launch(
    val id: String = "",
    val name: String = "",
    val flightNumber: Int = 0,
    val launchDate: String = "",
    val details: String = "",
    val imageUrl: String = "",
    val status: LaunchStatus = LaunchStatus.UPCOMING
)
