package com.manualcheg.ktscourse.screenRockets.data

import com.manualcheg.ktscourse.data.mappers.toEntity
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.RocketNetworkRepository
import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import com.manualcheg.ktscourse.screenRockets.domain.repository.RocketRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RocketRepositoryImpl(
    private val networkRepository: RocketNetworkRepository,
    private val databaseRepository: DatabaseRepository
) : RocketRepository {

    override suspend fun getRockets(query: String, page: Int): Result<Boolean> =
        withContext(Dispatchers.IO) {
            val result = networkRepository.getRockets(query, page)
            if (result.isSuccess) {
                val response = result.getOrThrow()
                val entities = response.docs.map { it.toEntity() }

                if (page == 1) {
                    databaseRepository.fetchAndSaveRocketsTransaction(entities)
                } else {
                    databaseRepository.insertRockets(entities)
                }

                Result.success(response.hasNextPage)
            } else {
                val exception =
                    result.exceptionOrNull() ?: Exception("Unknown error fetching rockets")
                Napier.e("Error in getRockets", exception)
                Result.failure(exception)
            }
        }

    override suspend fun getPagedRockets(query: String, page: Int, limit: Int): List<Rocket> {
        return databaseRepository.getPagedRocketsFromDb(query, page, limit)
    }

    override suspend fun toggleFavorite(rocket: RocketDetails): Result<Boolean> {
        databaseRepository.toggleFavorite(rocket.toEntity())
        return Result.success(!rocket.isFavorite)
    }
}
