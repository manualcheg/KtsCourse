package com.manualcheg.ktscourse.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.manualcheg.ktscourse.data.database.entity.RocketEntity

@Dao
interface RocketDao {
    @Query("SELECT * FROM rockets ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getRocketsPaged(limit: Int, offset: Int): List<RocketEntity>

    @Query("SELECT * FROM rockets WHERE name LIKE '%' || :query || '%' ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun searchRocketsPaged(query: String, limit: Int, offset: Int): List<RocketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRockets(rockets: List<RocketEntity>)

    @Query("DELETE FROM rockets")
    suspend fun deleteAllRockets()

    @Transaction
    suspend fun fetchAndSaveRocketsTransaction(rockets: List<RocketEntity>) {
        deleteAllRockets()
        insertRockets(rockets)
    }
}
