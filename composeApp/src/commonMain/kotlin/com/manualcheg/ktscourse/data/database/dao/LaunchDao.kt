package com.manualcheg.ktscourse.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.manualcheg.ktscourse.common.LaunchStatus
import com.manualcheg.ktscourse.data.database.entity.LaunchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {
    @Query("""
        SELECT * FROM launches
        WHERE (:rocketId IS NULL OR :rocketId = '' OR rocketId = :rocketId)
        AND (:hasStatuses = 0 OR status IN (:statuses))
        ORDER BY
            CASE WHEN :isDesc = 1 THEN launchDate END DESC,
            CASE WHEN :isDesc = 0 THEN launchDate END ASC,
            id ASC
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getLaunchesPaged(
        limit: Int,
        offset: Int,
        rocketId: String?,
        statuses: List<LaunchStatus>?,
        hasStatuses: Boolean,
        isDesc: Boolean
    ): List<LaunchEntity>

    @Query("""
        SELECT * FROM launches
        WHERE (name LIKE '%' || :query || '%' OR details LIKE '%' || :query || '%')
        AND (:rocketId IS NULL OR :rocketId = '' OR rocketId = :rocketId)
        AND (:hasStatuses = 0 OR status IN (:statuses))
        ORDER BY
            CASE WHEN :isDesc = 1 THEN launchDate END DESC,
            CASE WHEN :isDesc = 0 THEN launchDate END ASC,
            id ASC
        LIMIT :limit OFFSET :offset
    """)
    suspend fun searchLaunchesPaged(
        query: String,
        limit: Int,
        offset: Int,
        rocketId: String?,
        statuses: List<LaunchStatus>?,
        hasStatuses: Boolean,
        isDesc: Boolean
    ): List<LaunchEntity>

    @Query("SELECT * FROM launches")
    fun getAllLaunches(): Flow<List<LaunchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Query("DELETE FROM launches")
    suspend fun deleteAllLaunches()

    @Query("SELECT * FROM launches WHERE id = :id")
    suspend fun getLaunchById(id: String): LaunchEntity?

    @Transaction
    suspend fun fetchAndSaveLaunchesTransaction(launches: List<LaunchEntity>) {
        deleteAllLaunches()
        insertLaunches(launches)
    }
}
