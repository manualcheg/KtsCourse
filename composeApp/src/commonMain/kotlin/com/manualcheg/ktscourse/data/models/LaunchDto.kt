package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDto(
    @SerialName("auto_update") val autoUpdate: Boolean?,
    @SerialName("cores") val cores: List<Core>,
    @SerialName("date_local") val dateLocal: String?,
    @SerialName("date_precision") val datePrecision: String?,
    @SerialName("date_unix") val dateUnix: Int?,
    @SerialName("date_utc") val dateUtc: String?,
    @SerialName("details") val details: String? = null,
    @SerialName("failures") val failures: List<Failure>? = null,
    @SerialName("fairings") val fairings: Fairings? = null,
    @SerialName("flight_number") val flightNumber: Int,
    @SerialName("id") val id: String,
    @SerialName("launchpad") val launchpad: String?,
    @SerialName("links") val links: Links?,
    @SerialName("name") val name: String? = "",
    @SerialName("net") val net: Boolean?,
    @SerialName("payloads") val payloads: List<String?>,
    @SerialName("rocket") val rocket: String?,
    @SerialName("ships") val ships: List<String?>,
    @SerialName("static_fire_date_unix") val staticFireDateUnix: Int?,
    @SerialName("static_fire_date_utc") val staticFireDateUtc: String?,
    @SerialName("success") val success: Boolean?,
    @SerialName("tbd") val tbd: Boolean?,
    @SerialName("upcoming") val upcoming: Boolean?,
    @SerialName("window") val window: Int?
) {

    @Serializable
    data class Core(
        @SerialName("core") val core: String?,
        @SerialName("flight") val flight: Int?,
        @SerialName("gridfins") val gridfins: Boolean?,
        @SerialName("landing_attempt") val landingAttempt: Boolean?,
        @SerialName("landing_success") val landingSuccess: Boolean?,
        @SerialName("landing_type") val landingType: String?,
        @SerialName("landpad") val landpad: String?,
        @SerialName("legs") val legs: Boolean?,
        @SerialName("reused") val reused: Boolean?
    )

    @Serializable
    data class Failure(
        @SerialName("altitude") val altitude: Int? = 0,
        @SerialName("reason") val reason: String? = "",
        @SerialName("time") val time: Int? = 0
    )

    @Serializable
    data class Fairings(
        @SerialName("recovered") val recovered: Boolean?,
        @SerialName("recovery_attempt") val recoveryAttempt: Boolean?,
        @SerialName("reused") val reused: Boolean?,
        @SerialName("ships") val ships: List<String?>
    )

    @Serializable
    data class Links(
        @SerialName("article") val article: String?,
        @SerialName("flickr") val flickr: Flickr?,
        @SerialName("patch") val patch: Patch?,
        @SerialName("presskit") val presskit: String?,
        @SerialName("reddit") val reddit: Reddit?,
        @SerialName("webcast") val webcast: String?,
        @SerialName("wikipedia") val wikipedia: String?,
        @SerialName("youtube_id") val youtubeId: String?
    ) {

        @Serializable
        data class Flickr(
            @SerialName("original") val original: List<String>,
            @SerialName("small") val small: List<String>
        )

        @Serializable
        data class Patch(
            @SerialName("large") val large: String?,
            @SerialName("small") val small: String?
        )

        @Serializable
        data class Reddit(
            @SerialName("campaign") val campaign: String?,
            @SerialName("launch") val launch: String?,
            @SerialName("media") val media: String?,
            @SerialName("recovery") val recovery: String?
        )
    }
}
