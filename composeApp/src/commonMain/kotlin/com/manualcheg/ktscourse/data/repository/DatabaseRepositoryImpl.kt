package com.manualcheg.ktscourse.data.repository

import com.manualcheg.ktscourse.data.database.AppDatabase
import com.manualcheg.ktscourse.data.database.LaunchDao
import com.manualcheg.ktscourse.data.database.LaunchEntity
import com.manualcheg.ktscourse.data.database.toDomain
import com.manualcheg.ktscourse.screenMain.domain.model.Launch

class DatabaseRepositoryImpl(database: AppDatabase) : DatabaseRepository {
    val launchDao: LaunchDao = database.launchDao()
    override suspend fun getPagedLaunchesFromDb(
        query: String,
        page: Int,
        limit: Int
    ): List<Launch> {
        val offset = (page - 1) * limit
        val entities = if (query.isBlank()) {
            launchDao.getLaunchesPaged(limit, offset)
        } else {
            launchDao.searchLaunchesPaged(query, limit, offset)
        }
        return entities.map { it.toDomain() }
    }

    override suspend fun fetchAndSaveLaunchesTransaction(entities: List<LaunchEntity>) {
        launchDao.fetchAndSaveLaunchesTransaction(entities)
    }

    override suspend fun insertLaunches(entities: List<LaunchEntity>) {
        launchDao.insertLaunches(entities)
    }

    override suspend fun deleteAllLaunches() {
        launchDao.deleteAllLaunches()
    }
}
