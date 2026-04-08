package com.manualcheg.ktscourse.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey
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
    val payloadWeights: String?, // JSON
    val firstStage: String?, // JSON
    val secondStage: String?, // JSON
    val engines: String?, // JSON
    val landingLegs: String?, // JSON
    val flickrImages: String?, // JSON
    val isFavorite: Boolean = false
)
