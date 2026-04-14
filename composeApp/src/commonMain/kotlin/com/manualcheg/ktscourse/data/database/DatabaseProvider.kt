package com.manualcheg.ktscourse.data.database

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
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

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `rockets` (
                `id` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                `active` INTEGER NOT NULL,
                `firstFlight` TEXT,
                `description` TEXT,
                `imageUrl` TEXT,
                `country` TEXT,
                `costPerLaunch` INTEGER,
                `height` REAL,
                `diameter` REAL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent(),
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `favorite_rockets` (
                `id` TEXT NOT NULL,
                `name` TEXT NOT NULL,
                `active` INTEGER NOT NULL,
                `firstFlight` TEXT,
                `description` TEXT,
                `imageUrl` TEXT,
                `country` TEXT,
                `costPerLaunch` INTEGER,
                `height` REAL,
                `diameter` REAL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent(),
        )
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `type` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `stages` INTEGER")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `boosters` INTEGER")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `successRatePct` INTEGER")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `company` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `wikipedia` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `mass` REAL")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `payloadWeights` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `firstStage` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `secondStage` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `engines` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `landingLegs` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `flickrImages` TEXT")
        connection.execSQL("ALTER TABLE `rockets` ADD COLUMN `isFavorite` INTEGER NOT NULL DEFAULT 0")

        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `type` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `stages` INTEGER")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `boosters` INTEGER")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `successRatePct` INTEGER")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `company` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `wikipedia` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `mass` REAL")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `payloadWeights` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `firstStage` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `secondStage` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `engines` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `landingLegs` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `flickrImages` TEXT")
        connection.execSQL("ALTER TABLE `favorite_rockets` ADD COLUMN `isFavorite` INTEGER NOT NULL DEFAULT 0")

    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE `launches` ADD COLUMN `rocketId` TEXT")
    }
}

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE `launches` ADD COLUMN `launchpad` TEXT")
    }
}


fun getAppDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_8)
        .build()
}
