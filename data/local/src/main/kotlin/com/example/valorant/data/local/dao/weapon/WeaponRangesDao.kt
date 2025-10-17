package com.example.valorant.data.local.dao.weapon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorant.data.local.model.weapon.WeaponRangesEntity
import com.example.valorant.data.local.model.weapon.WeaponSkinEntity

@Dao
interface WeaponRangesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRange(range: WeaponRangesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRanges(ranges: List<WeaponRangesEntity>)

    @Query("SELECT * FROM weapon_ranges WHERE weapon_uuid = :weaponUuid")
    suspend fun getRangesByWeapon(weaponUuid: String): List<WeaponRangesEntity>

    @Query("DELETE FROM weapon_ranges WHERE weapon_uuid = :weaponUuid")
    suspend fun deleteRangesByWeapon(weaponUuid: String)
}