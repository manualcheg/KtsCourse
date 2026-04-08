package com.manualcheg.ktscourse.screenLaunchDetails.domain.model

import com.manualcheg.ktscourse.common.LaunchStatus

data class LaunchDetails(
    val id: String,
    val name: String,
    val dateUtc: String,
    val dateLocal: String,
    val flightNumber: Int,
    val patchUrl: String?,
    val status: LaunchStatus,
    val details: String?,
    val rocketName: String,
    val rocketId: String,
    val launchpadName: String,
    val payloads: List<String>,
    val articleUrl: String?,
    val wikipediaUrl: String?,
    val youtubeUrl: String?,
    val redditUrl: String?,
    val isFavorite: Boolean
)
