package com.example.valorant.data.source.repository.map

import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.map.MapCalloutDao
import com.example.valorant.data.local.dao.map.MapDao
import com.example.valorant.data.local.mappers.map.toDetail
import com.example.valorant.data.local.mappers.map.toLight
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.network.model.dto.map.MapDTO
import com.example.valorant.data.network.service.map.MapService
import com.example.valorant.data.source.mapper.map.toCalloutEntities
import com.example.valorant.data.source.mapper.map.toEntity
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
    private val mapDao: MapDao,
    private val mapCalloutDao: MapCalloutDao,
    private val dataUpdateDao: DataUpdateDao,
): MapRepository {

    override fun getMaps(): Flow<StateListWrapper<MapLight>> {
        return flow {
            val localMaps = mapDao.getAllMaps()
            val shouldFetch = dataUpdateDao.isUpdateExpired(DATA_TYPE, System.currentTimeMillis())
            val dataExists = dataUpdateDao.doesDataExist(DATA_TYPE) > 0

            if(localMaps.isNotEmpty() && !shouldFetch) {
                emit(StateListWrapper(localMaps.map { it.toLight() }))
            } else {
                emit(StateListWrapper.loading())

                when(val mapsResult = mapService.getMaps()) {
                    is Resource.Success -> {
                        val maps = mapsResult.data.data
                        saveMapsToDatabase(maps)

                        val savedMaps = mapDao.getAllMaps()

                        if(savedMaps.isNotEmpty()) {
                            if(dataExists) {
                                dataUpdateDao.updateNextUpdateTime(DATA_TYPE, System.currentTimeMillis(), UPDATE_INTERVAL)
                            } else {
                                val nextUpdateAt = System.currentTimeMillis() + UPDATE_INTERVAL
                                val dataUpdate = DataUpdateEntity(
                                    dataType = DATA_TYPE,
                                    lastUpdatedAt = System.currentTimeMillis(),
                                    nextUpdateAt = nextUpdateAt,
                                )

                                dataUpdateDao.insertUpdate(dataUpdate)
                            }
                        }

                        val data = savedMaps.map { it.toLight() }
                        emit(StateListWrapper(data))
                    }
                    is Resource.Error -> {
                        if (localMaps.isEmpty()) {
                            emit(StateListWrapper(error = mapsResult.error))
                        }
                    }
                    is Resource.Loading -> {
                        emit(StateListWrapper.loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMapDetail(uuid: String): Flow<StateWrapper<MapDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val mapsWithCallouts = mapDao.getMapWithCallouts(uuid)
            if(mapsWithCallouts != null) {
                emit(StateWrapper(mapsWithCallouts.toDetail()))
            } else {
                when(val mapResult = mapService.getMapDetail(uuid)) {
                    is Resource.Success -> {
                        val mapData = mapResult.data.data

                        if(mapData != null) {
                            saveMapsToDatabase(listOf(mapData))

                            val savedMap = mapDao.getMapWithCallouts(uuid)

                            if(savedMap != null) {
                                emit(StateWrapper(savedMap.toDetail()))
                            }
                        }
                    }
                    is Resource.Error -> {
                        emit(StateWrapper(error = mapResult.error))
                    }
                    is Resource.Loading -> {
                        emit(StateWrapper.loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun saveMapsToDatabase(maps: List<MapDTO>) {
        maps.forEach { map ->
            try {
                val mapEntity = map.toEntity()
                val calloutEntities = map.toCalloutEntities()

                mapDao.insertMap(mapEntity)
                if(calloutEntities != null) {
                    mapCalloutDao.insertCallouts(calloutEntities)
                }
            } catch (e: Exception) {
                throw RuntimeException("Failed to save map ${map.uuid} (${map.displayName}): ${e.message}")
            }
        }
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 30 * 60 * 1000
        const val DATA_TYPE = "maps"
    }
}