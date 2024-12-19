package com.example.valorant.data.network.model.dto.agent.abilities

import com.example.valorant.domain.model.agent.abilities.AgentAbilitiesType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentAbilitiesDTO(
    @SerialName("slot")
    val slot: AgentAbilitiesType,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("description")
    val description: String,
    @SerialName("displayIcon")
    val displayIcon: String,
)