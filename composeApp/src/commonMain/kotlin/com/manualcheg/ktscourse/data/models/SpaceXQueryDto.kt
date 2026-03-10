package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpaceXQueryDto(
    val query: SpaceXQueryInnerDto = SpaceXQueryInnerDto(),
    val options: SpaceXOptionsDto
)

@Serializable
data class SpaceXQueryInnerDto(
    @SerialName("\$text") val text: SpaceXTextSearchDto? = null
)

@Serializable
data class SpaceXTextSearchDto(
    @SerialName("\$search") val search: String
)

@Serializable
data class SpaceXOptionsDto(
    val page: Int,
    val limit: Int = 10,
    val sort: Map<String, Int> = mapOf("date_utc" to 1)
)
