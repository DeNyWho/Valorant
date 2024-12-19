package com.example.valorant.data.network.service.map

import com.example.valorant.data.network.api.ApiEndpoints
import com.example.valorant.data.network.model.dto.common.ValorantApiDTO
import com.example.valorant.data.network.model.dto.common.ValorantApiListDTO
import com.example.valorant.data.network.model.dto.map.detail.MapDetailDTO
import com.example.valorant.data.network.model.dto.map.light.MapLightDTO
import com.example.valorant.data.network.safeApiCall
import com.example.valorant.domain.model.common.request.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.encodedPath
import javax.inject.Inject

class MapService @Inject constructor(
    private val client: HttpClient,
) {
    suspend fun getMaps(): Resource<ValorantApiListDTO<MapLightDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = ApiEndpoints.VALORANT_API_MAPS
            }
        }

        return safeApiCall<ValorantApiListDTO<MapLightDTO>>(client, request)
    }

    suspend fun getMapDetail(uuid: String): Resource<ValorantApiDTO<MapDetailDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.VALORANT_API_MAPS}/$uuid"
            }
        }

        return safeApiCall<ValorantApiDTO<MapDetailDTO>>(client, request)
    }
}