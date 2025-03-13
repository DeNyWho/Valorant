package com.example.valorant.data.local.model.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "agents",
    foreignKeys = [
        ForeignKey(
            entity = AgentRoleEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["roleUuid"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("roleUuid")],
)
data class AgentEntity(
    @PrimaryKey
    val uuid: String,
    val displayName: String,
    val displayIcon: String,
    val description: String,
    val fullPortrait: String,
    val fullPortraitV2: String,
    val background: String,
    val roleUuid: String,
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    val characterTags: List<String>?
)