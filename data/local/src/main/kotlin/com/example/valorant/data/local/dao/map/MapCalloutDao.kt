package com.example.valorant.data.local.dao.map

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.valorant.data.local.model.map.MapCalloutEntity

@Dao
interface MapCalloutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCallouts(callouts: List<MapCalloutEntity>)
}