package com.manualcheg.ktscourse.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SpaceXResponseDto<T>(
    val docs: List<T>,
    val totalDocs: Int,
    val offset: Int? = 0,
    val limit: Int,
    val totalPages: Int,
    val page: Int,
    val pagingCounter: Int,
    val hasPrevPage: Boolean,
    val hasNextPage: Boolean,
    val prevPage: Int? = null,
    val nextPage: Int? = null
) {
    fun <R> map(transform: (T) -> R): SpaceXResponseDto<R> {
        return SpaceXResponseDto(
            docs = docs.map(transform),
            totalDocs = totalDocs,
            offset = offset,
            limit = limit,
            totalPages = totalPages,
            page = page,
            pagingCounter = pagingCounter,
            hasPrevPage = hasPrevPage,
            hasNextPage = hasNextPage,
            prevPage = prevPage,
            nextPage = nextPage
        )
    }
}
