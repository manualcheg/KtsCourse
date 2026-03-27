package com.manualcheg.ktscourse.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/spacex.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() },
    )
}
