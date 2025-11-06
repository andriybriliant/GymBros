package com.andriibryliant.gymbros.presentation.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class WorkoutViewModel(
    private val useCases: WorkoutUseCases
): ViewModel(){
    private val _workouts = useCases.getAllWorkoutsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val workouts = _workouts
}