package com.manualcheg.ktscourse.data.mappers

import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.data.database.entity.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.entity.LaunchEntity
import com.manualcheg.ktscourse.data.models.LaunchDetailsDto
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails

fun LaunchDto.toDomain(): Launch {
    return Launch(
        id = id,
        name = name ?: "",
        rocketId = rocket ?: "",
        flightNumber = flightNumber,
        launchDate = dateUtc ?: "",
        details = details ?: "",
        imageUrl = links?.patch?.small ?: "",
        status = when {
            upcoming == true -> LaunchStatus.UPCOMING
            success == true -> LaunchStatus.SUCCESS
            else -> LaunchStatus.FAILURE
        },
        launchpad = launchpad ?: "",
    )
}

fun LaunchDto.toEntity(): LaunchEntity {
    return LaunchEntity(
        id = id,
        name = name ?: "",
        flightNumber = flightNumber,
        launchDate = dateUtc ?: "",
        details = details ?: "",
        imageUrl = links?.patch?.small ?: "",
        status = when {
            upcoming == true -> LaunchStatus.UPCOMING
            success == true -> LaunchStatus.SUCCESS
            else -> LaunchStatus.FAILURE
        },
        rocketId = rocket,
        launchpad = launchpad ?: "",
    )
}

fun LaunchEntity.toDomain() =
    Launch(
        id = id,
        name = name,
        rocketId = rocketId ?: "",
        flightNumber = flightNumber,
        launchDate = launchDate,
        details = details,
        imageUrl = imageUrl,
        status = status,
        launchpad = launchpad ?: "",
    )

fun FavoriteLaunchEntity.toDomain() =
    Launch(
        id = launchId,
        name = name ?: "",
        rocketId = rocketId ?: "",
        flightNumber = flightNumber ?: 0,
        launchDate = dateUtc ?: "",
        details = details ?: "",
        imageUrl = patchUrl ?: "",
        status = status ?: LaunchStatus.FAILURE,
        launchpad = launchpadName ?: "",
    )

fun FavoriteLaunchEntity.toDomainDetails() =
    LaunchDetails(
        id = launchId,
        name = name ?: "",
        dateUtc = dateUtc ?: "",
        dateLocal = dateLocal ?: "",
        flightNumber = flightNumber ?: 0,
        patchUrl = patchUrl,
        status = status ?: LaunchStatus.FAILURE,
        details = details,
        rocketName = rocketName ?: "",
        rocketId = rocketId ?: "",
        launchpadName = launchpadName ?: "",
        payloads = payloads?.filterNotNull() ?: emptyList(),
        articleUrl = articleUrl,
        wikipediaUrl = wikipediaUrl,
        youtubeUrl = youtubeUrl,
        redditUrl = redditUrl,
        isFavorite = isFavorite ?: false,
    )

fun LaunchDetailsDto.toDomain(isFavorite: Boolean): LaunchDetails {
    return LaunchDetails(
        id = id,
        name = name ?: "",
        dateUtc = dateUtc ?: "",
        dateLocal = dateLocal ?: "",
        flightNumber = flightNumber ?: 0,
        patchUrl = links?.patch?.large,
        status = when {
            upcoming == true -> LaunchStatus.UPCOMING
            success == true -> LaunchStatus.SUCCESS
            else -> LaunchStatus.FAILURE
        },
        details = details,
        rocketName = rocket,
        rocketId = rocket ?: "",
        launchpadName = launchpad,
        payloads = payloads?.filterNotNull() ?: emptyList(),
        articleUrl = links?.article,
        wikipediaUrl = links?.wikipedia,
        youtubeUrl = links?.webcast,
        redditUrl = links?.reddit?.launch,
        isFavorite = isFavorite,
    )
}

fun LaunchDetails.toFavoriteEntity(): FavoriteLaunchEntity {
    return FavoriteLaunchEntity(
        launchId = id,
        name = name,
        dateUtc = dateUtc,
        dateLocal = dateLocal,
        flightNumber = flightNumber,
        patchUrl = patchUrl,
        status = status,
        details = details,
        rocketName = rocketName,
        rocketId = rocketId,
        launchpadName = launchpadName,
        payloads = payloads,
        articleUrl = articleUrl,
        wikipediaUrl = wikipediaUrl,
        youtubeUrl = youtubeUrl,
        redditUrl = redditUrl,
        isFavorite = true,
    )
}
