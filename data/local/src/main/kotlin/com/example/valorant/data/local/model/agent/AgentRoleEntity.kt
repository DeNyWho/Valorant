package com.example.valorant.data.local.model.agent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agent_roles")
data class AgentRoleEntity(
    @PrimaryKey
    val uuid: String,
    val displayName: String,
    val displayIcon: String
)