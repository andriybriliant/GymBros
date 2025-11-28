package com.andriibryliant.gymbros.presentation.app

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppThemeController {
    val settings = Settings()

    private val _dynamicColor = MutableStateFlow(getDynamicColor())
    val dynamicColor = _dynamicColor.asStateFlow()

    fun saveDynamicColor(enabled: Boolean){
        settings.putBoolean("dynamic_color", enabled)
        _dynamicColor.value = enabled
    }

    fun getDynamicColor(): Boolean{
        return settings.getBoolean("dynamic_color", false)
    }
}