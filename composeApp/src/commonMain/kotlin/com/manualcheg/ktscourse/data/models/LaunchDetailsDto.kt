package com.manualcheg.ktscourse.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDetailsDto(
    @SerialName("auto_update") val autoUpdate: Boolean? = null,
    @SerialName("capsules") val capsules: List<String>? = emptyList(),
    @SerialName("cores") val cores: List<Core>? = emptyList(),
    @SerialName("crew") val crew: List<String?>? = emptyList(),
    @SerialName("date_local") val dateLocal: String? = null,
    @SerialName("date_precision") val datePrecision: String? = null,
    @SerialName("date_unix") val dateUnix: Int? = null,
    @SerialName("date_utc") val dateUtc: String? = null,
    @SerialName("details") val details: String? = null,
    @SerialName("failures") val failures: List<Failure?>? = emptyList(),
    @SerialName("fairings") val fairings: Fairings? = null,
    @SerialName("flight_number") val flightNumber: Int? = null,
    @SerialName("id") val id: String = "",
    @SerialName("launchpad") val launchpad: String? = null,
    @SerialName("links") val links: Links? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("net") val net: Boolean? = null,
    @SerialName("payloads") val payloads: List<String?>? = emptyList(),
    @SerialName("rocket") val rocket: String? = null,
    @SerialName("ships") val ships: List<String?>? = emptyList(),
    @SerialName("static_fire_date_unix") val staticFireDateUnix: Int? = null,
    @SerialName("static_fire_date_utc") val staticFireDateUtc: String? = null,
    @SerialName("success") val success: Boolean? = null,
    @SerialName("tdb") val tdb: Boolean? = null,
    @SerialName("upcoming") val upcoming: Boolean? = null,
    @SerialName("window") val window: Int? = null,
) {
    @Serializable
    data class Core(
        @SerialName("core") val core: String? = null,
        @SerialName("flight") val flight: Int? = null,
        @SerialName("gridfins") val gridfins: Boolean? = null,
        @SerialName("landing_attempt") val landingAttempt: Boolean? = null,
        @SerialName("landing_success") val landingSuccess: Boolean? = null,
        @SerialName("landing_type") val landingType: String? = null,
        @SerialName("landpad") val landpad: String? = null,
        @SerialName("legs") val legs: Boolean? = null,
        @SerialName("reused") val reused: Boolean? = null,
    )

    @Serializable
    data class Links(
        @SerialName("article") val article: String? = null,
        @SerialName("flickr") val flickr: Flickr? = null,
        @SerialName("patch") val patch: Patch? = null,
        @SerialName("presskit") val presskit: String? = null,
        @SerialName("reddit") val reddit: Reddit? = null,
        @SerialName("webcast") val webcast: String? = null,
        @SerialName("wikipedia") val wikipedia: String? = null,
        @SerialName("youtube_id") val youtubeId: String? = null
    ) {
        @Serializable
        data class Flickr(
            @SerialName("original") val original: List<String?>? = emptyList(),
            @SerialName("small") val small: List<String?>? = emptyList()
        )

        @Serializable
        data class Patch(
            @SerialName("large") val large: String? = null,
            @SerialName("small") val small: String? = null,
        )

        @Serializable
        data class Reddit(
            @SerialName("campaign") val campaign: String? = null,
            @SerialName("launch") val launch: String? = null,
            @SerialName("media") val media: String? = null,
            @SerialName("recovery") val recovery: String? = null,
        )
    }

    @Serializable
    data class Failure(
        @SerialName("altitude") val altitude: Int? = 0,
        @SerialName("reason") val reason: String? = "",
        @SerialName("time") val time: Int? = 0
    )

    @Serializable
    data class Fairings(
        @SerialName("recovered") val recovered: Boolean? = null,
        @SerialName("recovery_attempt") val recoveryAttempt: Boolean? = null,
        @SerialName("reused") val reused: Boolean? = null,
        @SerialName("ships") val ships: List<String?>? = emptyList()
    )
}
