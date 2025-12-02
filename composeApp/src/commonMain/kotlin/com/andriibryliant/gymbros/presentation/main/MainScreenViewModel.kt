package com.andriibryliant.gymbros.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.MuscleGroup
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
    private val exerciseUseCases: ExerciseUseCases
) : ViewModel() {
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