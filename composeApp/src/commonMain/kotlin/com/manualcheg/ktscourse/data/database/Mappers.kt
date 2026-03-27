package com.manualcheg.ktscourse.data.database

import com.manualcheg.ktscourse.screenMain.domain.model.Launch

fun LaunchEntity.toDomain() =
    Launch(
        id = id,
        name = name,
        flightNumber = flightNumber,
        launchDate = launchDate,
        details = details,
        imageUrl = imageUrl,
        status = status,
    )

fun Launch.toEntity() =
    LaunchEntity(
        id = id,
        name = name,
        flightNumber = flightNumber,
        launchDate = launchDate,
        details = details,
        imageUrl = imageUrl,
        status = status,
    )
