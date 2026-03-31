package com.manualcheg.ktscourse.data.database

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "CREATE TABLE IF NOT EXISTS `favorite_launches` (`launchId` TEXT NOT NULL, PRIMARY KEY(`launchId`))",
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `name` TEXT DEFAULT ''")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `dateUtc` TEXT DEFAULT ''")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `dateLocal` TEXT DEFAULT ''")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `flightNumber` INTEGER DEFAULT 0")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `patchUrl` TEXT")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `status` TEXT DEFAULT 'UPCOMING'")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `details` TEXT")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `rocketName` TEXT DEFAULT ''")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `rocketId` TEXT DEFAULT ''")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `launchpadName` TEXT DEFAULT ''")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `payloads` TEXT") // Списки хранятся как TEXT через конвертеры
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `articleUrl` TEXT")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `wikipediaUrl` TEXT")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `youtubeUrl` TEXT")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `redditUrl` TEXT")
        connection.execSQL("ALTER TABLE `favorite_launches` ADD COLUMN `isFavorite` INTEGER DEFAULT 0")
    }
}

fun getAppDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        .setDriver(BundledSQLiteDriver())
        .build()
}
