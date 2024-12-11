package com.example.valorant.domain.state

import com.example.valorant.domain.model.common.request.ApiError

data class StateWrapper<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ApiError = ApiError(),
) {
    companion object {
        inline fun <reified T> loading(): StateWrapper<T> {
            return StateWrapper(isLoading = true)
        }
    }
}
