package com.example.valorant.data.network.di

import com.example.valorant.data.network.BuildConfig
import com.example.valorant.data.network.api.ApiEndpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = false
    }

    @Provides
    @Singleton
    fun providesHttpClient(json: Json): HttpClient = HttpClient(OkHttp) {
        engine {

        }
        install(ContentNegotiation) {
            json(json)
        }
        defaultRequest {
            header("Content-Type", "application/json")
            url {
                protocol = URLProtocol.HTTPS
                host = ApiEndpoints.VALORANT_API_HOST
                encodedPath = ApiEndpoints.VALORANT_API_VERSION
            }
        }
        if(BuildConfig.DEBUG) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
    }
}