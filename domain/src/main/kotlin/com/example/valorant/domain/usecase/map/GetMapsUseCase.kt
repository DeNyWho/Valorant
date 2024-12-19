package com.example.valorant.domain.usecase.map

import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.repository.map.MapRepository
import com.example.valorant.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetMapsUseCase(private val mapRepository: MapRepository) {
    operator fun invoke(): Flow<StateListWrapper<MapLight>> {
        return mapRepository.getMaps()
    }
}