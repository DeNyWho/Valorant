package com.example.valorant.data.source.repository.map

import com.example.valorant.data.network.service.map.MapService
import com.example.valorant.data.source.mapper.map.toDetail
import com.example.valorant.data.source.mapper.map.toLight
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.repository.map.MapRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class MapRepositoryImpl @Inject constructor(
    private val mapService: MapService,
): MapRepository {

    override fun getMaps(): Flow<StateListWrapper<MapLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val mapsResult = mapService.getMaps()) {
                is Resource.Success -> {
                    val data = mapsResult.data.data.map { it.toLight() }
                        .groupBy { it.displayName }
                        .filter { it.value.size == 1 }
                        .flatMap { it.value }
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = mapsResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getMapDetail(uuid: String): Flow<StateWrapper<MapDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val state = when(val mapResult = mapService.getMapDetail(uuid)) {
                is Resource.Success -> {
                    val data = mapResult.data.data?.toDetail()
                    StateWrapper(data)
                }
                is Resource.Error -> {
                    StateWrapper(error = mapResult.error)
                }
                is Resource.Loading -> {
                    StateWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}