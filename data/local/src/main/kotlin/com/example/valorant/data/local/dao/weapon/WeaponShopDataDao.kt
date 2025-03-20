package com.example.valorant.data.local.dao.weapon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.valorant.data.local.model.weapon.WeaponShopDataEntity

@Dao
interface WeaponShopDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopData(shopData: WeaponShopDataEntity)
}