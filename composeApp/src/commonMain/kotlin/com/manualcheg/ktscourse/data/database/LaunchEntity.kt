package com.manualcheg.ktscourse.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manualcheg.ktscourse.common.LaunchStatus

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val flightNumber: Int,
    val launchDate: String,
    val details: String,
    val imageUrl: String,
    val status: LaunchStatus
)
