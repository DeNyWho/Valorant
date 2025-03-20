package com.example.valorant.data.local.model.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "agent_roles",
    primaryKeys = ["uuid", "language_code"],
    indices = [Index(value = ["uuid"], unique = true)]
)
data class AgentRoleEntity(
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "display_icon")
    val displayIcon: String,
    @ColumnInfo(name = "language_code")
    val languageCode: String,
)