package com.example.valorant.data.network.model.dto.agent.role

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentRoleDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("displayIcon")
    val displayIcon: String,
)