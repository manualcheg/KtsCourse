package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.database.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.LaunchEntity
import com.manualcheg.ktscourse.screenMain.domain.model.Launch

interface DatabaseRepository {
    suspend fun getPagedLaunchesFromDb(query: String, page: Int, limit: Int): List<Launch>

    suspend fun fetchAndSaveLaunchesTransaction(entities: List<LaunchEntity>)

    suspend fun insertLaunches(entities: List<LaunchEntity>)

    suspend fun deleteAllLaunches()

    suspend fun toggleFavorite(launch: FavoriteLaunchEntity): Boolean

    suspend fun isFavorite(id: String): Boolean

    suspend fun getFavoriteLaunch(id: String): FavoriteLaunchEntity?
}
