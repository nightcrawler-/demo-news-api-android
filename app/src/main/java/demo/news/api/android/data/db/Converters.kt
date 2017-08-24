package com.cafrecode.obviator.data.db

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Frederick on 21/08/2017.
 */
class Converters {

    companion object {

        @JvmStatic
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @JvmStatic
        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }
}