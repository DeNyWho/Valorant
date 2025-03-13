package com.example.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.common.device.ThemeType
import com.example.valorant.domain.usecase.settings.theme.ThemeSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val themeSettingsUseCase: ThemeSettingsUseCase,
): ViewModel() {
    private val _selectedTheme: MutableStateFlow<ThemeType> =
        MutableStateFlow(ThemeType.SYSTEM)
    val selectedTheme: StateFlow<ThemeType> =
        _selectedTheme.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            launch { getThemeSettings() }
        }
    }

    private fun getThemeSettings() {
        themeSettingsUseCase.theme.onEach {
            _selectedTheme.value = it
        }.launchIn(viewModelScope)
    }

    fun updateThemeSettings(theme: ThemeType) {
        viewModelScope.launch {
            themeSettingsUseCase.updateTheme(theme)
        }
    }
}