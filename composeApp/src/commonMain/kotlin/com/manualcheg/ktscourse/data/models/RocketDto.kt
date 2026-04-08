package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String? = null,
    @SerialName("active") val active: Boolean,
    @SerialName("stages") val stages: Int? = null,
    @SerialName("boosters") val boosters: Int? = null,
    @SerialName("cost_per_launch") val costPerLaunch: Long? = null,
    @SerialName("success_rate_pct") val successRatePct: Int? = null,
    @SerialName("first_flight") val firstFlight: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("company") val company: String? = null,
    @SerialName("wikipedia") val wikipedia: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("flickr_images") val flickrImages: List<String> = emptyList(),
    @SerialName("height") val height: Dimension? = null,
    @SerialName("diameter") val diameter: Dimension? = null,
    @SerialName("mass") val mass: Mass? = null,
    @SerialName("payload_weights") val payloadWeights: List<PayloadWeight> = emptyList(),
    @SerialName("first_stage") val firstStage: FirstStage? = null,
    @SerialName("second_stage") val secondStage: SecondStage? = null,
    @SerialName("engines") val engines: EngineInfo? = null,
    @SerialName("landing_legs") val landingLegs: LandingLegs? = null
) {
    @Serializable
    data class Dimension(
        @SerialName("meters") val meters: Double? = null,
        @SerialName("feet") val feet: Double? = null
    )

    @Serializable
    data class Mass(
        @SerialName("kg") val kg: Double? = null,
        @SerialName("lb") val lb: Double? = null
    )

    @Serializable
    data class Thrust(
        @SerialName("kN") val kN: Double? = null,
        @SerialName("lbf") val lbf: Double? = null
    )

    @Serializable
    data class FirstStage(
        @SerialName("thrust_sea_level") val thrustSeaLevel: Thrust? = null,
        @SerialName("thrust_vacuum") val thrustVacuum: Thrust? = null,
        @SerialName("reusable") val reusable: Boolean? = null,
        @SerialName("engines") val engines: Int? = null,
        @SerialName("fuel_amount_tons") val fuelAmountTons: Double? = null,
        @SerialName("burn_time_sec") val burnTimeSec: Int? = null
    )

    @Serializable
    data class SecondStage(
        @SerialName("thrust") val thrust: Thrust? = null,
        @SerialName("payloads") val payloads: Payloads? = null,
        @SerialName("reusable") val reusable: Boolean? = null,
        @SerialName("engines") val engines: Int? = null,
        @SerialName("fuel_amount_tons") val fuelAmountTons: Double? = null,
        @SerialName("burn_time_sec") val burnTimeSec: Int? = null
    )

    @Serializable
    data class Payloads(
        @SerialName("composite_fairing") val compositeFairing: CompositeFairing? = null,
        @SerialName("option_1") val option1: String? = null
    )

    @Serializable
    data class CompositeFairing(
        @SerialName("height") val height: Dimension? = null,
        @SerialName("diameter") val diameter: Dimension? = null
    )

    @Serializable
    data class EngineInfo(
        @SerialName("isp") val isp: Isp? = null,
        @SerialName("thrust_sea_level") val thrustSeaLevel: Thrust? = null,
        @SerialName("thrust_vacuum") val thrustVacuum: Thrust? = null,
        @SerialName("number") val number: Int? = null,
        @SerialName("type") val type: String? = null,
        @SerialName("version") val version: String? = null,
        @SerialName("layout") val layout: String? = null,
        @SerialName("engine_loss_max") val engineLossMax: Int? = null,
        @SerialName("propellant_1") val propellant1: String? = null,
        @SerialName("propellant_2") val propellant2: String? = null,
        @SerialName("thrust_to_weight") val thrustToWeight: Double? = null
    )

    @Serializable
    data class Isp(
        @SerialName("sea_level") val seaLevel: Int? = null,
        @SerialName("vacuum") val vacuum: Int? = null
    )

    @Serializable
    data class LandingLegs(
        @SerialName("number") val number: Int? = null,
        @SerialName("material") val material: String? = null
    )

    @Serializable
    data class PayloadWeight(
        @SerialName("id") val id: String? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("kg") val kg: Double? = null,
        @SerialName("lb") val lb: Double? = null
    )
}
