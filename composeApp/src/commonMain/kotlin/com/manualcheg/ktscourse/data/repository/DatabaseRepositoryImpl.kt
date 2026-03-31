package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.data.database.AppDatabase
import com.manualcheg.ktscourse.data.database.FavoriteDao
import com.manualcheg.ktscourse.data.database.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.LaunchDao
import com.manualcheg.ktscourse.data.database.LaunchEntity
import com.manualcheg.ktscourse.data.database.toDomain
import com.manualcheg.ktscourse.screenLaunchDetails.domain.model.LaunchDetails
import com.manualcheg.ktscourse.screenMain.domain.model.Launch
import io.github.aakira.napier.Napier

class DatabaseRepositoryImpl(database: AppDatabase) : DatabaseRepository {
    private val launchDao: LaunchDao = database.launchDao()
    private val favoriteDao: FavoriteDao = database.favoriteDao()

    override suspend fun getPagedLaunchesFromDb(
        query: String,
        page: Int,
        limit: Int
    ): List<Launch> {
        return try {
            val offset = (page - 1) * limit
            val entities = if (query.isBlank()) {
                launchDao.getLaunchesPaged(limit, offset)
            } else {
                launchDao.searchLaunchesPaged(query, limit, offset)
            }
            entities.map { it.toDomain() }
        } catch (e: Exception) {
            Napier.e("Failed to get paged launches from DB", e)
            emptyList()
        }
    }

    override suspend fun fetchAndSaveLaunchesTransaction(entities: List<LaunchEntity>) {
        try {
            launchDao.fetchAndSaveLaunchesTransaction(entities)
        } catch (e: Exception) {
            Napier.e("Failed to execute fetchAndSaveLaunchesTransaction", e)
        }
    }

    override suspend fun insertLaunches(entities: List<LaunchEntity>) {
        try {
            launchDao.insertLaunches(entities)
        } catch (e: Exception) {
            Napier.e("Failed to insert launches into DB", e)
        }
    }

    override suspend fun deleteAllLaunches() {
        try {
            launchDao.deleteAllLaunches()
        } catch (e: Exception) {
            Napier.e("Failed to delete all launches from DB", e)
        }
    }

    override suspend fun toggleFavorite(launch: FavoriteLaunchEntity): Boolean {
        return try {
            val isCurrentlyFavorite = favoriteDao.isFavorite(launch.launchId)
            if (isCurrentlyFavorite) {
                favoriteDao.deleteFavorite(launch.launchId)
            } else {
                favoriteDao.insertFavorite(launch)
            }
            !isCurrentlyFavorite
        } catch (e: Exception) {
            Napier.e("Failed to toggle favorite for launchId: ${launch.launchId}", e)
            false
        }
    }

    override suspend fun isFavorite(id: String): Boolean {
        return try {
            favoriteDao.isFavorite(id)
        } catch (e: Exception) {
            Napier.e("Failed to check isFavorite for id: $id", e)
            false
        }
    }

    override suspend fun getFavoriteLaunch(id: String): FavoriteLaunchEntity? {
        return try {
            favoriteDao.getFavoriteLaunch(id)
        } catch (e: Exception) {
            Napier.e("Failed to get favorite launch for id: $id", e)
            null
        }
    }
}

fun LaunchDetails.toFavoriteEntity(): FavoriteLaunchEntity {
    return FavoriteLaunchEntity(
        launchId = id,
        name = name,
        dateUtc = dateUtc,
        dateLocal = dateLocal,
        flightNumber = flightNumber,
        patchUrl = patchUrl,
        status = LaunchStatus.valueOf(status.name),
        details = details,
        rocketName = rocketName,
        rocketId = rocketId,
        launchpadName = launchpadName,
        payloads = payloads,
        articleUrl = articleUrl,
        wikipediaUrl = wikipediaUrl,
        youtubeUrl = youtubeUrl,
        redditUrl = redditUrl,
        isFavorite = true,
    )
}
