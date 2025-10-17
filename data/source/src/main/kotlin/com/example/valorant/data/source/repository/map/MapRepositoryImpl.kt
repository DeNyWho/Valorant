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
import com.example.valorant.domain.model.common.request.ApiError
import com.example.valorant.domain.model.common.request.ApiError.Unknown
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.repository.map.MapRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.collections.immutable.toPersistentList
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

    override fun getMaps(): Flow<StateListWrapper<MapLight>> = flow {
        emit(StateListWrapper.loading())

        val localMaps = mapDao.getAllMaps()
        val shouldFetch = dataUpdateDao.isUpdateExpired(DATA_TYPE, System.currentTimeMillis())
        val dataExists = dataUpdateDao.doesDataExist(DATA_TYPE) > 0

        if (localMaps.isNotEmpty() && !shouldFetch) {
            emit(StateListWrapper.success(localMaps.map { it.toLight() }.toPersistentList()))
            return@flow
        }

        val state = when (val result = mapService.getMaps()) {
            is Resource.Success -> {
                try {
                    saveMapsToDatabase(result.data.data)
                    val savedMaps = mapDao.getAllMaps()

                    if (savedMaps.isNotEmpty()) {
                        updateDataTimestamp(dataExists)
                        StateListWrapper.success(savedMaps.map { it.toLight() }.toPersistentList())
                    } else {
                        StateListWrapper.error(
                            error = Unknown("No maps saved to database")
                        )
                    }
                } catch (e: Exception) {
                    StateListWrapper.error(
                        error = Unknown("Failed to save maps: ${e.message}", e)
                    )
                }
            }

            is Resource.Error -> {
                if (localMaps.isNotEmpty()) {
                    StateListWrapper.success(localMaps.map { it.toLight() }.toPersistentList())
                } else {
                    StateListWrapper.error(error = result.error)
                }
            }

            is Resource.Loading -> {
                StateListWrapper.loading()
            }
        }

        emit(state)
    }.flowOn(Dispatchers.IO)

    override fun getMapDetail(uuid: String): Flow<StateWrapper<MapDetail>> = flow {
        emit(StateWrapper.loading())

        val localMap = mapDao.getMapWithCallouts(uuid)

        if (localMap != null) {
            emit(StateWrapper.success(localMap.toDetail()))
            return@flow
        }

        val state = when (val result = mapService.getMapDetail(uuid)) {
            is Resource.Success -> {
                val mapData = result.data.data

                if (mapData == null) {
                    StateWrapper.error(
                        error = ApiError.HttpError(
                            statusCode = 404,
                            message = "Map not found on server"
                        )
                    )
                } else {
                    try {
                        saveMapsToDatabase(listOf(mapData))
                        val savedMap = mapDao.getMapWithCallouts(uuid)

                        if (savedMap != null) {
                            StateWrapper.success(savedMap.toDetail())
                        } else {
                            StateWrapper.error(
                                error = ApiError.Unknown("Failed to retrieve saved map from database")
                            )
                        }
                    } catch (e: Exception) {
                        StateWrapper.error(
                            error = ApiError.Unknown("Failed to save map: ${e.message}", e)
                        )
                    }
                }
            }

            is Resource.Error -> {
                StateWrapper.error(error = result.error)
            }

            is Resource.Loading -> {
                StateWrapper.loading()
            }
        }

        emit(state)
    }.flowOn(Dispatchers.IO)

    private suspend fun saveMapsToDatabase(maps: List<MapDTO>) {
        if (maps.isEmpty()) return

        maps.forEach { map ->
            try {
                // Map with ID 5914d1e0-40c4-cfdd-6b88-eba06347686c have bugs with images, so we skip it
                if (map.uuid == BUGGY_MAP_UUID) {
                    return@forEach
                }

                val mapEntity = map.toEntity()
                mapDao.insertMap(mapEntity)

                val calloutEntities = map.toCalloutEntities()
                if (calloutEntities != null && calloutEntities.isNotEmpty()) {
                    mapCalloutDao.insertCallouts(calloutEntities)
                }
            } catch (e: Exception) {
                throw RuntimeException(
                    "Failed to save map ${map.uuid} (${map.displayName}): ${e.message}",
                    e
                )
            }
        }
    }

    private suspend fun updateDataTimestamp(exists: Boolean) {
        val now = System.currentTimeMillis()

        if (exists) {
            dataUpdateDao.updateNextUpdateTime(DATA_TYPE, now, UPDATE_INTERVAL)
        } else {
            dataUpdateDao.insertUpdate(
                DataUpdateEntity(
                    dataType = DATA_TYPE,
                    lastUpdatedAt = now,
                    nextUpdateAt = now + UPDATE_INTERVAL,
                )
            )
        }
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 30 * 60 * 1000
        const val DATA_TYPE = "maps"
        const val BUGGY_MAP_UUID = "5914d1e0-40c4-cfdd-6b88-eba06347686c"
    }
}