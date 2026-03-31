package com.manualcheg.ktscourse.data.database

import androidx.room.TypeConverter
import com.manualcheg.ktscourse.common.LaunchStatus
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromStatus(status: LaunchStatus?): String? = status?.name

    @TypeConverter
    fun toStatus(value: String?): LaunchStatus? = value?.let {
        try {
            LaunchStatus.valueOf(it)
        } catch (e: Exception) {
            Napier.e("Failed to convert string to LaunchStatus: $it", e)
            null
        }
    }

    @TypeConverter
    fun fromStringList(value: List<String?>?): String? = value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String?>? = value?.let {
        try {
            Json.decodeFromString(it)
        } catch (e: Exception) {
            Napier.e("Failed to decode string list from JSON: $value", e)
            null
        }
    }
}
