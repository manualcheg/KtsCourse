package com.manualcheg.ktscourse.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDto(
    @SerialName("ceo")
    val ceo: String,
    @SerialName("coo")
    val coo: String,
    @SerialName("cto")
    val cto: String,
    @SerialName("cto_propulsion")
    val ctoPropulsion: String,
    @SerialName("employees")
    val employees: Int,
    @SerialName("founded")
    val founded: Int,
    @SerialName("founder")
    val founder: String,
    @SerialName("headquarters")
    val headquarters: Headquarters,
    @SerialName("id")
    val id: String,
    @SerialName("launch_sites")
    val launchSites: Int,
    @SerialName("links")
    val links: Links,
    @SerialName("name")
    val name: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("test_sites")
    val testSites: Int,
    @SerialName("valuation")
    val valuation: Long,
    @SerialName("vehicles")
    val vehicles: Int
) {
    @Serializable
    data class Headquarters(
        @SerialName("address")
        val address: String,
        @SerialName("city")
        val city: String,
        @SerialName("state")
        val state: String
    )

    @Serializable
    data class Links(
        @SerialName("elon_twitter")
        val elonTwitter: String,
        @SerialName("flickr")
        val flickr: String,
        @SerialName("twitter")
        val twitter: String,
        @SerialName("website")
        val website: String
    )
}
