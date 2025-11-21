package com.andriibryliant.gymbros.presentation.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class WorkoutViewModel(
    useCases: WorkoutUseCases
): ViewModel(){
    private val _workouts = useCases.getAllWorkoutsUseCase()
        .flowOn(Dispatchers.IO)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val workouts = _workouts
}