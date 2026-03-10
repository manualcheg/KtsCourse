package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.Launch
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.LaunchStatus
import com.manualcheg.ktscourse.data.models.SpaceXOptionsDto
import com.manualcheg.ktscourse.data.models.SpaceXQueryDto
import com.manualcheg.ktscourse.data.models.SpaceXQueryInnerDto
import com.manualcheg.ktscourse.data.models.SpaceXResponseDto
import com.manualcheg.ktscourse.data.models.SpaceXSearchNameDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkRepositoryImpl : NetworkRepository {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getAllLaunches(
        query: String,
        page: Int
    ): Result<SpaceXResponseDto<Launch>> {
        return try {
            val requestBody = SpaceXQueryDto(
                query = SpaceXQueryInnerDto(
                    name = if (query.isBlank()) null else SpaceXSearchNameDto(regex = query)
                ),
                options = SpaceXOptionsDto(page = page, limit = 10)
            )
            val response: SpaceXResponseDto<LaunchDto> =
                httpClient.post("https://api.spacexdata.com/v4/launches/query") {
                    setBody(requestBody)
                    contentType(ContentType.Application.Json)
                }.body()

            Result.success(response.map { dto ->
                val status = when {
                    dto.upcoming == true -> LaunchStatus.UPCOMING
                    dto.success == true -> LaunchStatus.SUCCESS
                    else -> LaunchStatus.FAILURE
                }
                Launch(
                    id = dto.id,
                    name = dto.name ?: "",
                    flightNumber = dto.flightNumber,
                    launchDate = dto.dateUtc ?: "",
                    details = dto.details ?: "",
                    imageUrl = dto.links?.patch?.small ?: "",
                    status = status
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
