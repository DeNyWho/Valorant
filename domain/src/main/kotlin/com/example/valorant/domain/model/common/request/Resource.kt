package com.example.valorant.domain.model.common.request

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error(
        val error: ApiError,
    ) : Resource<Nothing>()
}

sealed class ApiError(
    open val message: String,
    open val isRetryable: Boolean = false,
) {
    data class Network(
        override val message: String = "Network error",
        override val isRetryable: Boolean = true,
    ) : ApiError(message, isRetryable)

    data class Timeout(
        override val message: String = "Request timeout",
        override val isRetryable: Boolean = true,
    ) : ApiError(message, isRetryable)

    data class HttpError(
        val statusCode: Int,
        override val message: String,
        override val isRetryable: Boolean = statusCode >= 500,
    ) : ApiError(message, isRetryable)

    data class ParseError(
        override val message: String = "Failed to parse response",
        override val isRetryable: Boolean = false,
    ) : ApiError(message, isRetryable)

    data class Unknown(
        override val message: String,
        val exception: Throwable? = null,
        override val isRetryable: Boolean = false,
    ) : ApiError(message, isRetryable)

    override fun toString(): String = "${this::class.simpleName}: $message"
}