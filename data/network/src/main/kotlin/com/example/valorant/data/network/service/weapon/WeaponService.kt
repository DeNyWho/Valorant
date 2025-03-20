package com.example.valorant.data.network.service.weapon

import com.example.valorant.data.network.api.ApiEndpoints
import com.example.valorant.data.network.model.dto.common.ValorantApiDTO
import com.example.valorant.data.network.model.dto.common.ValorantApiListDTO
import com.example.valorant.data.network.model.dto.weapon.WeaponDTO
import com.example.valorant.data.network.model.dto.weapon.detail.WeaponDetailDTO
import com.example.valorant.data.network.safeApiCall
import com.example.valorant.domain.model.common.request.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.encodedPath
import javax.inject.Inject

class WeaponService @Inject constructor(
    private val client: HttpClient,
) {
    suspend fun getWeapons(): Resource<ValorantApiListDTO<WeaponDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = ApiEndpoints.VALORANT_API_WEAPONS
            }
        }

        return safeApiCall<ValorantApiListDTO<WeaponDTO>>(client, request)
    }

    suspend fun getWeaponDetail(uuid: String): Resource<ValorantApiDTO<WeaponDetailDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.VALORANT_API_WEAPONS}/$uuid"
            }
        }

        return safeApiCall<ValorantApiDTO<WeaponDetailDTO>>(client, request)
    }
}