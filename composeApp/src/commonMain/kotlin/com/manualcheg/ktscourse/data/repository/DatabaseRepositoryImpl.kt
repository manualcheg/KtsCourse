package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.database.AppDatabase
import com.manualcheg.ktscourse.data.database.dao.FavoriteDao
import com.manualcheg.ktscourse.data.database.entity.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.entity.FavoriteRocketEntity
import com.manualcheg.ktscourse.data.database.dao.LaunchDao
import com.manualcheg.ktscourse.data.database.entity.LaunchEntity
import com.manualcheg.ktscourse.data.database.dao.RocketDao
import com.manualcheg.ktscourse.data.database.entity.RocketEntity
import com.manualcheg.ktscourse.data.mappers.toDomain
import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepositoryImpl(database: AppDatabase) : DatabaseRepository {
    private val launchDao: LaunchDao = database.launchDao()
    private val favoriteDao: FavoriteDao = database.favoriteDao()
    private val rocketDao: RocketDao = database.rocketDao()

    override suspend fun getPagedLaunchesFromDb(
        query: String,
        rocketId: String?,
        page: Int,
        limit: Int
    ): List<Launch> {
        return try {
            val offset = (page - 1) * limit
            val entities = if (query.isBlank()) {
                launchDao.getLaunchesPaged(limit, offset, rocketId)
            } else {
                launchDao.searchLaunchesPaged(query, limit, offset, rocketId)
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

    override suspend fun getPagedRocketsFromDb(query: String, page: Int, limit: Int): List<Rocket> {
        return try {
            val offset = (page - 1) * limit
            val entities = if (query.isBlank()) {
                rocketDao.getRocketsPaged(limit, offset)
            } else {
                rocketDao.searchRocketsPaged(query, limit, offset)
            }
            entities.map { entity ->
                val isFavorite = favoriteDao.isFavorite(entity.id)
                entity.toDomain().copy(isFavorite = isFavorite)
            }
        } catch (e: Exception) {
            Napier.e("Failed to get paged rockets from DB", e)
            emptyList()
        }
    }

    override suspend fun fetchAndSaveRocketsTransaction(entities: List<RocketEntity>) {
        try {
            rocketDao.fetchAndSaveRocketsTransaction(entities)
        } catch (e: Exception) {
            Napier.e("Failed to execute fetchAndSaveRocketsTransaction", e)
        }
    }

    override suspend fun insertRockets(entities: List<RocketEntity>) {
        try {
            rocketDao.insertRockets(entities)
        } catch (e: Exception) {
            Napier.e("Failed to insert rockets into DB", e)
        }
    }

    override suspend fun deleteAllRockets() {
        try {
            rocketDao.deleteAllRockets()
        } catch (e: Exception) {
            Napier.e("Failed to delete all rockets from DB", e)
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

    override suspend fun toggleFavorite(rocket: FavoriteRocketEntity): Boolean {
        return try {
            val isCurrentlyFavorite = favoriteDao.isRocketFavorite(rocket.id)
            if (isCurrentlyFavorite) {
                favoriteDao.deleteFavoriteRocket(rocket.id)
            } else {
                favoriteDao.insertFavoriteRocket(rocket)
            }
            !isCurrentlyFavorite
        } catch (e: Exception) {
            Napier.e("Failed to toggle favorite for rocketId: ${rocket.id}", e)
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

    override fun getAllFavoriteLaunches(): Flow<List<Launch>> {
        return favoriteDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllFavoriteRockets(): Flow<List<Rocket>> {
        return favoriteDao.getAllFavoriteRockets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun clearAllTables() {
        launchDao.deleteAllLaunches()
        rocketDao.deleteAllRockets()
        favoriteDao.deleteAllFavorites()
    }
}
