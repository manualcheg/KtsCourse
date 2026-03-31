package com.manualcheg.ktscourse.screenLaunchDetails.domain.model

import com.manualcheg.ktscourse.common.LaunchStatus

data class LaunchDetails(
    val id: String = "",
    val name: String = "",
    val dateUtc: String = "",
    val dateLocal: String = "",
    val flightNumber: Int = 0,
    val patchUrl: String? = null,
    val status: LaunchStatus = LaunchStatus.UPCOMING,
    val details: String? = null,
    val rocketName: String = "",
    val rocketId: String = "",
    val launchpadName: String = "",
    val payloads: List<String> = emptyList(),
    val articleUrl: String? = null,
    val wikipediaUrl: String? = null,
    val youtubeUrl: String? = null,
    val redditUrl: String? = null,
    val isFavorite: Boolean = false
)
