package com.manualcheg.ktscourse.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.manualcheg.ktscourse.data.database.entity.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.entity.FavoriteRocketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteLaunchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteRocketEntity)

    @Query("DELETE FROM favorite_launches WHERE launchId = :launchId")
    suspend fun deleteFavorite(launchId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_launches WHERE launchId = :launchId)")
    suspend fun isFavorite(launchId: String): Boolean

    @Query("SELECT * FROM favorite_launches WHERE launchId = :launchId")
    suspend fun getFavoriteLaunch(launchId: String): FavoriteLaunchEntity?

    @Query("SELECT * FROM favorite_launches")
    fun getAllFavorites(): Flow<List<FavoriteLaunchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRocket(rocket: FavoriteRocketEntity)

    @Query("DELETE FROM favorite_rockets WHERE id = :id")
    suspend fun deleteFavoriteRocket(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_rockets WHERE id = :id)")
    suspend fun isRocketFavorite(id: String): Boolean

    @Query("SELECT * FROM favorite_rockets WHERE id = :id")
    suspend fun getFavoriteRocket(id: String): FavoriteRocketEntity?

    @Query("SELECT * FROM favorite_rockets")
    fun getAllFavoriteRockets(): Flow<List<FavoriteRocketEntity>>

    @Query("DELETE FROM favorite_rockets")
    suspend fun deleteFavoriteRocket()

    @Query("DELETE FROM favorite_launches")
    suspend fun deleteFavoriteLaunch()

    @Transaction
    suspend fun deleteAllFavorites(){
        deleteFavoriteLaunch()
        deleteFavoriteRocket()
    }
}
