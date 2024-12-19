package com.example.valorant.data.network.model.dto.agent.detail

import com.example.valorant.data.network.model.dto.agent.abilities.AgentAbilitiesDTO
import com.example.valorant.data.network.model.dto.agent.role.AgentRoleDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentDetailDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("description")
    val description: String,
    @SerialName("characterTags")
    val characterTags: List<String>? = null,
    @SerialName("fullPortrait")
    val fullPortrait: String,
    @SerialName("background")
    val background: String,
    @SerialName("role")
    val role: AgentRoleDTO,
    @SerialName("abilities")
    val abilities: List<AgentAbilitiesDTO>,
)