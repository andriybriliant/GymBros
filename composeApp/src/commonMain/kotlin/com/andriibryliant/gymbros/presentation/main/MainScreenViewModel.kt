package com.andriibryliant.gymbros.presentation.main

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _selectedTabIndex.value
        )

    fun onSelectedTabChange(index: Int){
        _selectedTabIndex.value = index
    }
}