package com.example.valorant.domain.state

import com.example.valorant.domain.model.common.request.ApiError

data class StateListWrapper<T>(
    val data: List<T> = listOf(),
    val isLoading: Boolean = false,
    val error: ApiError? = null,
) {
    companion object {
        inline fun <reified T> loading(): StateListWrapper<T> {
            return StateListWrapper(isLoading = true)
        }
    }
}
