package com.manualcheg.ktscourse.data.mappers

import com.manualcheg.ktscourse.data.database.entity.FavoriteRocketEntity
import com.manualcheg.ktscourse.data.database.entity.RocketEntity
import com.manualcheg.ktscourse.data.models.RocketDto
import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val json = Json { ignoreUnknownKeys = true }

fun RocketDto.toEntity(): RocketEntity {
    return RocketEntity(
        id = id,
        name = name,
        type = type,
        active = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = flickrImages.firstOrNull(),
        height = height?.meters,
        diameter = diameter?.meters,
        mass = mass?.kg,
        payloadWeights = json.encodeToString(payloadWeights),
        firstStage = json.encodeToString(firstStage),
        secondStage = json.encodeToString(secondStage),
        engines = json.encodeToString(engines),
        landingLegs = json.encodeToString(landingLegs),
        flickrImages = json.encodeToString(flickrImages),
        isFavorite = false,
    )
}

fun RocketEntity.toDomain(): Rocket {
    return Rocket(
        id = id,
        name = name,
        type = type,
        isActive = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = imageUrl,
        height = height,
        diameter = diameter,
        mass = mass,
        isFavorite = isFavorite,
    )
}

fun FavoriteRocketEntity.toDomain(): Rocket {
    return Rocket(
        id = id,
        name = name,
        type = type,
        isActive = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = imageUrl,
        height = height,
        diameter = diameter,
        mass = mass,
        isFavorite = true,
    )
}

fun Rocket.toEntity(): FavoriteRocketEntity {
    return FavoriteRocketEntity(
        id = id,
        name = name,
        type = type,
        active = isActive,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = imageUrl,
        height = height,
        diameter = diameter,
        mass = mass,
        payloadWeights = null,
        firstStage = null,
        secondStage = null,
        engines = null,
        landingLegs = null,
        flickrImages = null,
        isFavorite = true,
    )
}

fun RocketDetails.toEntity(): FavoriteRocketEntity {
    return FavoriteRocketEntity(
        id = id,
        name = name,
        type = type,
        active = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = imageUrl,
        height = height,
        diameter = diameter,
        mass = mass,
        payloadWeights = json.encodeToString(
            payloadWeights.map {
                RocketDto.PayloadWeight(name = it.first, kg = it.second)
            },
        ),
        firstStage = firstStage,
        secondStage = secondStage,
        engines = engines,
        landingLegs = landingLegs,
        flickrImages = json.encodeToString(flickrImages),
        isFavorite = true,
    )
}

fun RocketDto.toDomainDetails(isFavorite: Boolean = false): RocketDetails {
    return RocketDetails(
        id = id,
        name = name,
        type = type,
        active = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = flickrImages.firstOrNull(),
        height = height?.meters,
        diameter = diameter?.meters,
        mass = mass?.kg,
        payloadWeights = payloadWeights.map { it.name.orEmpty() to (it.kg ?: 0.0) },
        firstStage = json.encodeToString(firstStage),
        secondStage = json.encodeToString(secondStage),
        engines = json.encodeToString(engines),
        landingLegs = json.encodeToString(landingLegs),
        flickrImages = flickrImages,
        isFavorite = isFavorite,
    )
}

fun RocketEntity.toDomainDetails(): RocketDetails {
    return RocketDetails(
        id = id,
        name = name,
        type = type,
        active = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = imageUrl,
        height = height,
        diameter = diameter,
        mass = mass,
        payloadWeights = parsePayloadWeights(payloadWeights),
        firstStage = firstStage,
        secondStage = secondStage,
        engines = engines,
        landingLegs = landingLegs,
        flickrImages = parseStringList(flickrImages),
        isFavorite = isFavorite,
    )
}

fun FavoriteRocketEntity.toDomainDetails(): RocketDetails {
    return RocketDetails(
        id = id,
        name = name,
        type = type,
        active = active,
        stages = stages,
        boosters = boosters,
        costPerLaunch = costPerLaunch,
        successRatePct = successRatePct,
        firstFlight = firstFlight,
        country = country,
        company = company,
        wikipedia = wikipedia,
        description = description,
        imageUrl = imageUrl,
        height = height,
        diameter = diameter,
        mass = mass,
        payloadWeights = parsePayloadWeights(payloadWeights),
        firstStage = firstStage,
        secondStage = secondStage,
        engines = engines,
        landingLegs = landingLegs,
        flickrImages = parseStringList(flickrImages),
        isFavorite = true,
    )
}

private fun parseStringList(jsonString: String?): List<String> {
    return try {
        jsonString?.let {
            json.parseToJsonElement(it).jsonArray.map { it.jsonPrimitive.content }
        } ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}

private fun parsePayloadWeights(jsonString: String?): List<Pair<String, Double>> {
    return try {
        jsonString?.let {
            json.parseToJsonElement(it).jsonArray.map {
                val obj = it.jsonObject
                val name = obj["name"]?.jsonPrimitive?.content ?: "Unknown"
                val kg = obj["kg"]?.jsonPrimitive?.content?.toDoubleOrNull() ?: 0.0
                name to kg
            }
        } ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
}
