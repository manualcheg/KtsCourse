package com.manualcheg.ktscourse.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manualcheg.ktscourse.common.LaunchStatus

@Entity(tableName = "favorite_launches")
data class FavoriteLaunchEntity(
    @PrimaryKey
    val launchId: String,
    val name: String? = "",
    val dateUtc: String? = "",
    val dateLocal: String? = "",
    val flightNumber: Int? = 0,
    val patchUrl: String? = null,
    val status: LaunchStatus? = LaunchStatus.UPCOMING,
    val details: String? = null,
    val rocketName: String? = "",
    val rocketId: String? = "",
    val launchpadName: String? = "",
    val payloads: List<String?>? = emptyList(),
    val articleUrl: String? = null,
    val wikipediaUrl: String? = null,
    val youtubeUrl: String? = null,
    val redditUrl: String? = null,
    val isFavorite: Boolean? = false,
)
