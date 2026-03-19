package com.manualcheg.ktscourse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {
    @Query("SELECT * FROM launches ORDER BY flightNumber ASC LIMIT :limit OFFSET :offset")
    suspend fun getLaunchesPaged(limit: Int, offset: Int): List<LaunchEntity>

    @Query("SELECT * FROM launches WHERE name LIKE '%' || :query || '%' OR details LIKE '%' || :query || '%' ORDER BY flightNumber ASC LIMIT :limit OFFSET :offset")
    suspend fun searchLaunchesPaged(query: String, limit: Int, offset: Int): List<LaunchEntity>

    @Query("SELECT * FROM launches")
    fun getAllLaunches(): Flow<List<LaunchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Query("DELETE FROM launches")
    suspend fun deleteAllLaunches()

    @Query("SELECT * FROM launches WHERE id = :id")
    suspend fun getLaunchById(id: String): LaunchEntity?

    @Transaction
    suspend fun fetchAndSaveLaunchesTransaction(launches: List<LaunchEntity>){
        deleteAllLaunches()
        insertLaunches(launches)
    }
}
