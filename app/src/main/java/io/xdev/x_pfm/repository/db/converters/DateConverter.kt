package io.xdev.x_pfm.repository.db.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(time: Long?): Date? {
        return if (time == null) null else Date(time)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
