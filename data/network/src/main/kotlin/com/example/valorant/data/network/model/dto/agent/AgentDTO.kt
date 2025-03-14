package com.example.valorant.data.network.model.dto.agent

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgentDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("displayIcon")
    val displayIcon: String,
    @SerialName("description")
    val description: String,
    @SerialName("characterTags")
    val characterTags: List<String>? = null,
    @SerialName("fullPortrait")
    val fullPortrait: String,
    @SerialName("fullPortraitV2")
    val fullPortraitV2: String,
    @SerialName("background")
    val background: String,
    @SerialName("role")
    val role: AgentRoleDTO,
    @SerialName("abilities")
    val abilities: List<AgentAbilityDTO>,
)