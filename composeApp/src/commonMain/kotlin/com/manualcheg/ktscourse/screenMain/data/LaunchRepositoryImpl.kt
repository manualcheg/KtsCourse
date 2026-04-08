package com.manualcheg.ktscourse.screenMain.data

import com.manualcheg.ktscourse.data.mappers.toDomain
import com.manualcheg.ktscourse.data.mappers.toDomainDetails
import com.manualcheg.ktscourse.data.mappers.toFavoriteEntity
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.LaunchNetworkRepository
import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class LaunchRepositoryImpl(
    private val networkRepository: LaunchNetworkRepository,
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
                val exception =
                    result.exceptionOrNull() ?: Exception("Unknown error fetching launches")
                Napier.e("Error in fetchAndSaveLaunches", exception)
                Result.failure(exception)
            }
        }

    override suspend fun getLaunchById(id: String): Result<LaunchDetails> =
        withContext(Dispatchers.IO) {

            val favorite = databaseRepository.getFavoriteLaunch(id)
            if (favorite != null) {
                return@withContext Result.success(favorite.toDomainDetails())
            }

            val networkResult = networkRepository.getLaunch(id)
            if (networkResult.isSuccess) {
                val dto = networkResult.getOrThrow()
                Result.success(dto.toDomain(isFavorite = false))
            } else {
                Napier.e(
                    "Error in getLaunchById",
                    networkResult.exceptionOrNull() ?: Exception("Launch not found"),
                )
                Result.failure(networkResult.exceptionOrNull() ?: Exception("Launch not found"))
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
}
