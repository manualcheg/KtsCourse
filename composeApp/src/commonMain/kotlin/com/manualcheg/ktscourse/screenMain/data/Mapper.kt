package com.manualcheg.ktscourse.screenMain.data

import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.data.database.entity.LaunchEntity
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.domain.model.Launch

fun LaunchDto.toDomain(): Launch =
    Launch(
        id = id,
        name = name ?: "Unknown",
        flightNumber = flightNumber,
        launchDate = dateUtc ?: "Unknown",
        details = details ?: "",
        imageUrl = links?.patch?.small ?: "",
        status =
            when (success) {
                true -> LaunchStatus.SUCCESS
                false -> LaunchStatus.FAILURE
                null -> LaunchStatus.UPCOMING
            },
        rocketId = "",
        launchpad = launchpad ?: "",
    )

fun LaunchDto.toEntity(): LaunchEntity =
    LaunchEntity(
        id = id,
        name = name ?: "Unknown",
        flightNumber = flightNumber,
        launchDate = dateUtc ?: "Unknown",
        details = details ?: "",
        imageUrl = links?.patch?.small ?: "",
        status =
            when (success) {
                true -> LaunchStatus.SUCCESS
                false -> LaunchStatus.FAILURE
                null -> LaunchStatus.UPCOMING
            },
    )
