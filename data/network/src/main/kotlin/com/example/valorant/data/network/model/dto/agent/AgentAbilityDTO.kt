package com.example.valorant.data.network.model.dto.agent

import com.example.valorant.domain.model.agent.abilities.AgentAbilityType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentAbilityDTO(
    @SerialName("slot")
    val slot: AgentAbilityType,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("description")
    val description: String,
    @SerialName("displayIcon")
    val displayIcon: String?,
)