package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.LaunchDetailsDto
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.SpaceXOptionsDto
import com.manualcheg.ktscourse.data.models.SpaceXQueryDto
import com.manualcheg.ktscourse.data.models.SpaceXQueryInnerDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto
import com.manualcheg.ktscourse.data.models.SpaceXTextSearchDto
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class NetworkRepositoryImpl(private val httpClient: HttpClient) : NetworkRepository {

    override suspend fun getLaunches(
        query: String,
        page: Int
    ): Result<SpaceXResponseDto<LaunchDto>> {
        return try {
            val requestBody = SpaceXQueryDto(
                query = SpaceXQueryInnerDto(
                    text = if (query.isBlank()) null else SpaceXTextSearchDto(search = query),
                ),
                options = SpaceXOptionsDto(
                    page = page,
                    limit = 10,
                    sort = mapOf("flight_number" to 1),
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
}
