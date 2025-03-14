package com.example.valorant.data.local.model.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agent_roles")
data class AgentRoleEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "display_icon")
    val displayIcon: String
)