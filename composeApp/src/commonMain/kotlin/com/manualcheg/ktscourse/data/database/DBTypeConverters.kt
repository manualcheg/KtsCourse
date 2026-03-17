package com.manualcheg.ktscourse.data.database

import androidx.room.TypeConverter
import com.manualcheg.ktscourse.data.models.LaunchStatus

class DBTypeConverters {
    @TypeConverter
    fun fromLaunchStatus(status: LaunchStatus): String = status.name

    @TypeConverter
    fun toLaunchStatus(status: String): LaunchStatus = LaunchStatus.valueOf(status)
}
