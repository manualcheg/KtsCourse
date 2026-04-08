package com.manualcheg.ktscourse.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(value: String?): List<String?>? {
        return value?.let { json.decodeFromString(it) }
    }

    @TypeConverter
    fun toStringList(list: List<String?>?): String? {
        return list?.let { json.encodeToString(it) }
    }
}
