package com.example.valorant.data.network.model.dto.agent.light

import com.example.valorant.data.network.model.dto.agent.role.AgentRoleDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentLightDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("displayIcon")
    val displayIcon: String,
    @SerialName("role")
    val role: AgentRoleDTO,
)