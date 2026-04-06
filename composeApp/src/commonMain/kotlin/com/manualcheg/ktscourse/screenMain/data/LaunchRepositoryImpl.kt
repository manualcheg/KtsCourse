package com.manualcheg.ktscourse.screenMain.data

import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.data.models.LaunchDetailsDto
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.data.repository.toFavoriteEntity
import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class LaunchRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : LaunchRepository {
    override suspend fun getPagedLaunchesFromDb(
        query: String,
        page: Int,
        limit: Int
    ): List<Launch> {
        return databaseRepository.getPagedLaunchesFromDb(query, page, limit)
    }

    override suspend fun fetchAndSaveLaunches(query: String, page: Int): Result<Boolean> =
        withContext(Dispatchers.IO) {
            val result = networkRepository.getLaunches(query, page)
            if (result.isSuccess) {
                val response = result.getOrThrow()
                val entities = response.docs.map { it.toEntity() }

                if (page == 1 && query.isBlank()) {
                    databaseRepository.fetchAndSaveLaunchesTransaction(entities)
                } else {
                    databaseRepository.insertLaunches(entities)
                }

                Result.success(response.hasNextPage)
            } else {
                val exception = result.exceptionOrNull() ?: Exception("Unknown error fetching launches")
                Napier.e("Error in fetchAndSaveLaunches", exception)
                Result.failure(exception)
            }
        }

    override suspend fun getLaunchById(id: String): Result<LaunchDetails> =
        withContext(Dispatchers.IO) {
            val networkResult = networkRepository.getLaunch(id)
            if (networkResult.isSuccess) {
                val dto = networkResult.getOrThrow()
                val isFavorite = databaseRepository.isFavorite(id)
                Result.success(dto.toDomain(isFavorite))
            } else {
                val exception = networkResult.exceptionOrNull() ?: Exception("Unknown error fetching launch by id: $id")
                Napier.e("Error in getLaunchById for id: $id", exception)
                Result.failure(exception)
            }
        }

    override suspend fun toggleFavorite(launch: LaunchDetails): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val newState = databaseRepository.toggleFavorite(launch.toFavoriteEntity())
                Result.success(newState)
            } catch (e: Exception) {
                Napier.e("Error toggling favorite for id: ${launch.id}", e)
                Result.failure(e)
            }
        }

    private fun LaunchDetailsDto.toDomain(isFavorite: Boolean): LaunchDetails {
        return LaunchDetails(
            id = id,
            name = name ?: "",
            dateUtc = dateUtc ?: "",
            dateLocal = dateLocal ?: "",
            flightNumber = flightNumber ?: 0,
            patchUrl = links?.patch?.large,
            status = when {
                upcoming == true -> LaunchStatus.UPCOMING
                success == true -> LaunchStatus.SUCCESS
                else -> LaunchStatus.FAILURE
            },
            details = details,
            rocketName = "Rocket $rocket",
            rocketId = rocket ?: "",
            launchpadName = "Launchpad $launchpad",
            payloads = payloads?.filterNotNull() ?: emptyList(),
            articleUrl = links?.article,
            wikipediaUrl = links?.wikipedia,
            youtubeUrl = links?.webcast,
            redditUrl = links?.reddit?.launch,
            isFavorite = isFavorite,
        )
    }
}
