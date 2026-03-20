package com.manualcheg.ktscourse.data.database

import androidx.room.TypeConverter
import com.manualcheg.ktscourse.screenMain.domain.models.LaunchStatus

class Converters {
    @TypeConverter
    fun fromStatus(status: LaunchStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): LaunchStatus = LaunchStatus.valueOf(value)
}
