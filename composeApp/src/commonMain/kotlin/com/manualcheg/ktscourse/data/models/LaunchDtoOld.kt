package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDtoOld(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("flight_number") val flightNumber: Int,
    @SerialName("date_utc") val dateUtc: String,
    @SerialName("details") val details: String? = null,
    @SerialName("links") val links: LinksDto
)

@Serializable
data class LinksDto(
    @SerialName("patch") val patch: PatchDto
)

@Serializable
data class PatchDto(
    @SerialName("small") val small: String? = null
)
