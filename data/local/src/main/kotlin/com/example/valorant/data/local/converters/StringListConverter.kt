package com.example.valorant.data.local.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(it) }
    }
}