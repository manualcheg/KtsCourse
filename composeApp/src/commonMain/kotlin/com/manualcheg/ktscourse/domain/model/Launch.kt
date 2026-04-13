package com.manualcheg.ktscourse.domain.model

import com.manualcheg.ktscourse.common.LaunchStatus

data class Launch(
    val id: String,
    val name: String,
    val rocketId: String,
    val flightNumber: Int,
    val launchDate: String,
    val details: String,
    val imageUrl: String,
    val status: LaunchStatus
)
