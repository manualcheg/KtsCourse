package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.models.Launch
import com.manualcheg.ktscourse.data.models.LaunchDto
import com.manualcheg.ktscourse.data.models.LaunchStatus
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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

    override suspend fun getAllLaunches(): Result<List<Launch>> {
        return try {
            Napier.v { Result.toString() }
            val response: List<LaunchDto> =
                httpClient.get("https://api.spacexdata.com/v4/launches").body()
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
