package com.example.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings.model.SettingsEvent
import com.example.settings.model.SettingsState
import com.example.valorant.domain.model.common.device.ThemeType
import com.example.valorant.domain.usecase.settings.theme.ThemeSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val themeSettingsUseCase: ThemeSettingsUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        handleEvent(SettingsEvent.LoadInitialData)
    }

    fun handleEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.LoadInitialData -> loadInitialData()
            is SettingsEvent.SelectTheme -> updateThemeSettings(event.theme)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { loadThemeSettings() }
        }
    }

    private fun loadThemeSettings() {
        themeSettingsUseCase.theme.onEach { result ->
            _state.update {
                it.copy(
                    selectedTheme = result,
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun updateThemeSettings(theme: ThemeType) {
        viewModelScope.launch {
            themeSettingsUseCase.updateTheme(theme)
        }
    }
}