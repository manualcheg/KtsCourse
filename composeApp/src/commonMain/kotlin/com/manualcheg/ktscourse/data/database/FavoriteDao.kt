package com.manualcheg.ktscourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteLaunchEntity)

    @Query("DELETE FROM favorite_launches WHERE launchId = :launchId")
    suspend fun deleteFavorite(launchId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_launches WHERE launchId = :launchId)")
    suspend fun isFavorite(launchId: String): Boolean

    @Query("SELECT * FROM favorite_launches WHERE launchId = :launchId")
    suspend fun getFavoriteLaunch(launchId: String): FavoriteLaunchEntity?

    @Query("SELECT * FROM favorite_launches")
    suspend fun getAllFavorites(): List<FavoriteLaunchEntity>
}
