package com.example.valorant.data.local.model.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "agents",
    foreignKeys = [
        ForeignKey(
            entity = AgentRoleEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["role_uuid"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index("role_uuid"),
        Index(value = ["uuid"], unique = true)
    ],
    primaryKeys = ["uuid", "language_code"],
)
data class AgentEntity(
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "display_icon")
    val displayIcon: String,
    @ColumnInfo(name = "description", typeAffinity = ColumnInfo.TEXT)
    val description: String,
    @ColumnInfo(name = "full_portrait")
    val fullPortrait: String,
    @ColumnInfo(name = "full_portrait_v2")
    val fullPortraitV2: String,
    @ColumnInfo(name = "background")
    val background: String,
    @ColumnInfo(name = "role_uuid")
    val roleUuid: String,
    @ColumnInfo(name = "character_tags")
    val characterTags: List<String>?,
    @ColumnInfo(name = "language_code")
    val languageCode: String,
)