@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.manualcheg.ktscourse.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.manualcheg.ktscourse.data.database.dao.FavoriteDao
import com.manualcheg.ktscourse.data.database.dao.LaunchDao
import com.manualcheg.ktscourse.data.database.dao.RocketDao
import com.manualcheg.ktscourse.data.database.entity.FavoriteLaunchEntity
import com.manualcheg.ktscourse.data.database.entity.FavoriteRocketEntity
import com.manualcheg.ktscourse.data.database.entity.LaunchEntity
import com.manualcheg.ktscourse.data.database.entity.RocketEntity

@Database(entities = [LaunchEntity::class, FavoriteLaunchEntity::class, RocketEntity::class, FavoriteRocketEntity::class], version = 8)
@TypeConverters(Converters::class, DBTypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun rocketDao(): RocketDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
