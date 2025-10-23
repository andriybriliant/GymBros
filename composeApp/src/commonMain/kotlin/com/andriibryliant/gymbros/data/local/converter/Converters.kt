package com.andriibryliant.gymbros.data.local.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format


class Converters {
    private val formatter = LocalDate.Formats.ISO

    @TypeConverter
    fun fromDate(date: LocalDate?): String?{
        return date?.format(formatter)
    }

    @TypeConverter
    fun toDate(date: String?): LocalDate?{
        return date?.let { LocalDate.parse(it, formatter) }
    }
}