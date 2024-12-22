package com.example.valorant.data.network.service.agent

import com.example.valorant.data.network.api.ApiEndpoints
import com.example.valorant.data.network.model.dto.agent.detail.AgentDetailDTO
import com.example.valorant.data.network.model.dto.agent.light.AgentLightDTO
import com.example.valorant.data.network.model.dto.common.ValorantApiDTO
import com.example.valorant.data.network.model.dto.common.ValorantApiListDTO
import com.example.valorant.data.network.safeApiCall
import com.example.valorant.domain.model.common.request.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import io.ktor.http.encodedPath
import javax.inject.Inject

class AgentService @Inject constructor(
    private val client: HttpClient,
) {
    suspend fun getAgents(): Resource<ValorantApiListDTO<AgentLightDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = ApiEndpoints.VALORANT_API_AGENTS
            }
            parameter("isPlayableCharacter", true)
        }

        return safeApiCall<ValorantApiListDTO<AgentLightDTO>>(client, request)
    }

    suspend fun getAgentDetail(uuid: String): Resource<ValorantApiDTO<AgentDetailDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.VALORANT_API_AGENTS}/$uuid"
            }
        }

        return safeApiCall<ValorantApiDTO<AgentDetailDTO>>(client, request)
    }
}