package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryDto(
    @SerialName("details")
    val details: String,
    @SerialName("event_date_unix")
    val eventDateUnix: Int,
    @SerialName("event_date_utc")
    val eventDateUtc: String,
    @SerialName("links")
    val links: Links,
    @SerialName("title")
    val title: String
) {
    @Serializable
    data class Links(
        @SerialName("article")
        val article: String?
    )
}
