package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.database.entity.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.entity.FavoriteRocketEntity
import com.manualcheg.ktscourse.data.database.entity.LaunchEntity
import com.manualcheg.ktscourse.data.database.entity.RocketEntity
import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket

import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getPagedLaunchesFromDb(query: String, rocketId: String?, page: Int, limit: Int): List<Launch>

    suspend fun fetchAndSaveLaunchesTransaction(entities: List<LaunchEntity>)

    suspend fun insertLaunches(entities: List<LaunchEntity>)

    suspend fun deleteAllLaunches()

    suspend fun getPagedRocketsFromDb(query: String, page: Int, limit: Int): List<Rocket>

    suspend fun fetchAndSaveRocketsTransaction(entities: List<RocketEntity>)

    suspend fun insertRockets(entities: List<RocketEntity>)

    suspend fun deleteAllRockets()

    suspend fun clearAllTables()

    suspend fun toggleFavorite(launch: FavoriteLaunchEntity): Boolean

    suspend fun toggleFavorite(rocket: FavoriteRocketEntity): Boolean

    suspend fun isFavorite(id: String): Boolean

    suspend fun getFavoriteLaunch(id: String): FavoriteLaunchEntity?

    fun getAllFavoriteLaunches(): Flow<List<Launch>>

    fun getAllFavoriteRockets(): Flow<List<Rocket>>
}
