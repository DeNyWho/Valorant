package com.example.valorant.data.network.service.agent

import com.example.valorant.data.network.api.ApiEndpoints
import com.example.valorant.data.network.model.dto.agent.AgentDTO
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
    suspend fun getAgents(): Resource<ValorantApiListDTO<AgentDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = ApiEndpoints.VALORANT_API_AGENTS
            }
            parameter("isPlayableCharacter", true)
        }

        return safeApiCall<ValorantApiListDTO<AgentDTO>>(client, request)
    }

    suspend fun getAgentDetail(uuid: String): Resource<ValorantApiDTO<AgentDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.VALORANT_API_AGENTS}/$uuid"
            }
        }

        return safeApiCall<ValorantApiDTO<AgentDTO>>(client, request)
    }
}