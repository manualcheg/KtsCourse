package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.CompanyDto
import com.manualcheg.ktscourse.data.models.HistoryDto
import com.manualcheg.ktscourse.data.models.LaunchDetailsDto
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.RocketDto
import com.manualcheg.ktscourse.data.models.SpaceXOptionsDto
import com.manualcheg.ktscourse.data.models.SpaceXQueryDto
import com.manualcheg.ktscourse.data.models.SpaceXQueryInnerDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto
import com.manualcheg.ktscourse.data.models.SpaceXTextSearchDto
import com.manualcheg.ktscourse.domain.model.LaunchFilterType
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.math.ceil

class NetworkRepository(private val httpClient: HttpClient) :
    LaunchNetworkRepository,
    RocketNetworkRepository,
    CompanyNetworkRepository,
    HistoryNetworkRepository {
    override suspend fun getLaunches(
        query: String,
        rocketId: String?,
        filterType: LaunchFilterType,
        page: Int
    ): Result<SpaceXResponseDto<LaunchDto>> {
        if (query.isBlank() && rocketId.isNullOrBlank()) {
            when (filterType) {
                LaunchFilterType.Latest -> {
                    return getLatestLaunch().map {
                        wrapInResponse(it)
                    }
                }
                LaunchFilterType.Next -> {
                    return getNextLaunch().map {
                        wrapInResponse(it)
                    }
                }
                LaunchFilterType.Upcoming -> {
                    return getUpcomingLaunches().map { list ->
                        paginateList(list, page)
                    }
                }
                LaunchFilterType.Past -> {
                    return getPastLaunches().map { list ->
                        val sortedList = list.sortedByDescending { it.flightNumber }
                        paginateList(sortedList, page)
                    }
                }
                else -> {} // Для All продолжаем с query
            }
        }

        return try {
            val upcomingFilter = when (filterType) {
                LaunchFilterType.Past -> false
                LaunchFilterType.Upcoming -> true
                else -> null
            }

            val sort = when (filterType) {
                LaunchFilterType.Latest -> mapOf("flight_number" to -1)
                LaunchFilterType.Next -> mapOf("flight_number" to 1)
                LaunchFilterType.Upcoming -> mapOf("flight_number" to 1)
                LaunchFilterType.Past -> mapOf("flight_number" to -1)
                else -> mapOf("flight_number" to 1) // All (ASC)
            }

            val limit = when (filterType) {
                LaunchFilterType.Latest, LaunchFilterType.Next -> 1
                else -> 10
            }

            val requestBody = SpaceXQueryDto(
                query = SpaceXQueryInnerDto(
                    text = if (query.isBlank()) null else SpaceXTextSearchDto(search = query),
                    rocket = rocketId,
                    upcoming = upcomingFilter,
                ),
                options = SpaceXOptionsDto(
                    page = page,
                    limit = limit,
                    sort = sort,
                ),
            )
            val response: SpaceXResponseDto<LaunchDto> =
                httpClient.post("https://api.spacexdata.com/v4/launches/query") {
                    setBody(requestBody)
                    contentType(ContentType.Application.Json)
                }.body()

            Result.success(response)
        } catch (e: Exception) {
            Napier.e("No response from api.spacexdata.com/v4/launches/query", e)
            Result.failure(e)
        }
    }

    private fun paginateList(list: List<LaunchDto>, page: Int): SpaceXResponseDto<LaunchDto> {
        val limit = 10
        val totalDocs = list.size
        val totalPages = ceil(totalDocs.toDouble() / limit).toInt()
        val fromIndex = (page - 1) * limit
        val toIndex = (fromIndex + limit).coerceAtMost(totalDocs)

        val pagedList = if (fromIndex < totalDocs) {
            list.subList(fromIndex, toIndex)
        } else {
            emptyList()
        }

        return SpaceXResponseDto(
            docs = pagedList,
            totalDocs = totalDocs,
            limit = limit,
            totalPages = totalPages,
            page = page,
            pagingCounter = fromIndex + 1,
            hasPrevPage = page > 1,
            hasNextPage = page < totalPages,
            prevPage = if (page > 1) page - 1 else null,
            nextPage = if (page < totalPages) page + 1 else null,
        )
    }

    private fun wrapInResponse(launch: LaunchDto): SpaceXResponseDto<LaunchDto> {
        return SpaceXResponseDto(
            docs = listOf(launch),
            totalDocs = 1,
            limit = 1,
            totalPages = 1,
            page = 1,
            pagingCounter = 1,
            hasPrevPage = false,
            hasNextPage = false,
            prevPage = null,
            nextPage = null,
        )
    }

    override suspend fun getAllLaunches(): Result<List<LaunchDto>> {
        return try {
            val response: List<LaunchDto> =
                httpClient.get("https://api.spacexdata.com/v4/launches").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch all launches. Exception type: ${e::class.simpleName}", e)
            Result.failure(e)
        }
    }

    override suspend fun getPastLaunches(): Result<List<LaunchDto>> {
        return try {
            val response: List<LaunchDto> =
                httpClient.get("https://api.spacexdata.com/v4/launches/past").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch past launches", e)
            Result.failure(e)
        }
    }

    override suspend fun getUpcomingLaunches(): Result<List<LaunchDto>> {
        return try {
            val response: List<LaunchDto> =
                httpClient.get("https://api.spacexdata.com/v4/launches/upcoming").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch upcoming launches", e)
            Result.failure(e)
        }
    }

    override suspend fun getLatestLaunch(): Result<LaunchDto> {
        return try {
            val response: LaunchDto =
                httpClient.get("https://api.spacexdata.com/v4/launches/latest").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch latest launch", e)
            Result.failure(e)
        }
    }

    override suspend fun getNextLaunch(): Result<LaunchDto> {
        return try {
            val response: LaunchDto =
                httpClient.get("https://api.spacexdata.com/v4/launches/next").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch next launch", e)
            Result.failure(e)
        }
    }

    override suspend fun getLaunch(id: String): Result<LaunchDetailsDto> {
        if (id.isBlank()) return Result.failure(Exception("Launch ID is blank"))

        return try {
            val response: LaunchDetailsDto =
                httpClient.get("https://api.spacexdata.com/v4/launches/$id").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch launch by ID: $id", e)
            Result.failure(e)
        }
    }

    override suspend fun getRockets(
        query: String,
        page: Int
    ): Result<SpaceXResponseDto<RocketDto>> {
        return try {
            val requestBody = SpaceXQueryDto(
                query = SpaceXQueryInnerDto(
                    text = if (query.isBlank()) null else SpaceXTextSearchDto(search = query),
                ),
                options = SpaceXOptionsDto(
                    page = page,
                    limit = 10,
                    sort = mapOf("name" to 1),
                ),
            )
            val response: SpaceXResponseDto<RocketDto> =
                httpClient.post("https://api.spacexdata.com/v4/rockets/query") {
                    setBody(requestBody)
                    contentType(ContentType.Application.Json)
                }.body()

            Result.success(response)
        } catch (e: Exception) {
            Napier.e("No response from api.spacexdata.com/v4/rockets/query", e)
            Result.failure(e)
        }
    }

    override suspend fun getRocket(id: String): Result<RocketDto> {
        if (id.isBlank()) return Result.failure(Exception("Rocket ID is blank"))

        return try {
            val response: RocketDto =
                httpClient.get("https://api.spacexdata.com/v4/rockets/$id").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch rocket by ID: $id", e)
            Result.failure(e)
        }
    }

    override suspend fun getCompanyInfo(): Result<CompanyDto> {
        return try {
            val response: CompanyDto =
                httpClient.get("https://api.spacexdata.com/v4/company").body()
            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch company info", e)
            Result.failure(e)
        }
    }

    override suspend fun getHistoryInfo(): Result<List<HistoryDto>> {
        return try {
            val response =
                httpClient.get("https://api.spacexdata.com/v4/history")
                    .body<List<HistoryDto>>()

            Result.success(response)
        } catch (e: Exception) {
            Napier.e("Failed to fetch history info", e)
            Result.failure(e)
        }
    }
}
