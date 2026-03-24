package com.manualcheg.ktscourse.screenMain.data

import com.manualcheg.ktscourse.data.database.LaunchEntity
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.domain.model.LaunchStatus

fun LaunchDto.toDomain(): Launch {
    return Launch(
        id = id,
        name = name ?: "Unknown",
        flightNumber = flightNumber,
        launchDate = dateUtc ?: "Unknown",
        details = details ?: "",
        imageUrl = links?.patch?.small ?: "",
        status = when (success) {
            true -> LaunchStatus.SUCCESS
            false -> LaunchStatus.FAILURE
            null -> LaunchStatus.UPCOMING
        }
    )
}

fun LaunchDto.toEntity(): LaunchEntity {
    return LaunchEntity(
        id = id,
        name = name ?: "Unknown",
        flightNumber = flightNumber,
        launchDate = dateUtc ?: "Unknown",
        details = details ?: "",
        imageUrl = links?.patch?.small ?: "",
        status = when (success) {
            true -> LaunchStatus.SUCCESS
            false -> LaunchStatus.FAILURE
            null -> LaunchStatus.UPCOMING
        }
    )
}