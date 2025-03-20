package com.example.valorant.feature.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.state.StateWrapper
import com.example.valorant.domain.usecase.map.GetMapDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    private val getMapDetailUseCase: GetMapDetailUseCase,
): ViewModel() {
    private val _detailMap: MutableStateFlow<StateWrapper<MapDetail>> =
        MutableStateFlow(StateWrapper.loading())
    val detailMap: StateFlow<StateWrapper<MapDetail>> =
        _detailMap.asStateFlow()


    fun initialize(uuid: String) {
        viewModelScope.launch {
            getMap(uuid)
        }
    }

    private fun getMap(uuid: String) {
        getMapDetailUseCase.invoke(uuid).onEach {
            _detailMap.value = it
        }.launchIn(viewModelScope)
    }
}