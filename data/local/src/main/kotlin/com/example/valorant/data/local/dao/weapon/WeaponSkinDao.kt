package com.example.valorant.data.local.dao.weapon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorant.data.local.model.weapon.WeaponSkinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponSkinDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSkin(skin: WeaponSkinEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSkins(skins: List<WeaponSkinEntity>)

    @Query("SELECT * FROM weapon_skins WHERE weapon_uuid = :weaponUuid")
    suspend fun getSkinsByWeapon(weaponUuid: String): List<WeaponSkinEntity>

    @Query("DELETE FROM weapon_skins WHERE weapon_uuid = :weaponUuid")
    suspend fun deleteSkinsByWeapon(weaponUuid: String)
}