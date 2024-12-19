package com.example.valorant.domain.usecase.map

import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.repository.map.MapRepository
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

class GetMapDetailUseCase(private val mapRepository: MapRepository) {
    operator fun invoke(uuid: String): Flow<StateWrapper<MapDetail>> {
        return mapRepository.getMapDetail(uuid)
    }
}