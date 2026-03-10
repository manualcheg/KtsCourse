package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SpaceXQueryDto(
    val query: SpaceXQueryInnerDto = SpaceXQueryInnerDto(),
    val options: SpaceXOptionsDto
)

@Serializable
data class SpaceXQueryInnerDto(
    val name: SpaceXSearchNameDto? = null
)

@Serializable
data class SpaceXSearchNameDto(
    val regex: String,
    val options: String = "i"
)

@Serializable
data class SpaceXOptionsDto(
    val page: Int,
    val limit: Int = 10,
    val sort: Map<String, Int> = mapOf("date_utc" to 1)
)
