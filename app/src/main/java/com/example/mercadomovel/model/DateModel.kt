package com.example.mercadomovel.model

import androidx.room.TypeConverter
import java.util.Date

class DateModel {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}