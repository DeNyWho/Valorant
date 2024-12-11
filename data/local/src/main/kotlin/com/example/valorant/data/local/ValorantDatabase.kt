package com.example.valorant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.valorant.data.local.converters.LocalDateConverter
import com.example.valorant.data.local.converters.LocalDateTimeConverter

@Database(
    entities = [

    ],
    version = 0,
    exportSchema = true,
)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class)
internal abstract class ValorantDatabase : RoomDatabase() {

}