package com.example.valorant.domain.repository.map

import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    fun getMaps(): Flow<StateListWrapper<MapLight>>
    fun getMapDetail(uuid: String): Flow<StateWrapper<MapDetail>>
}