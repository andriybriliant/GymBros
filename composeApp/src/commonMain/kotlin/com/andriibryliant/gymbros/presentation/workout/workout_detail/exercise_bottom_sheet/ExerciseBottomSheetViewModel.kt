package com.andriibryliant.gymbros.presentation.workout.workout_detail.exercise_bottom_sheet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExerciseBottomSheetViewModel(
    private val useCases: WorkoutUseCases,
    private val exerciseUseCases: ExerciseUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _sets = MutableStateFlow<List<Set>>(emptyList())
    var sets = _sets.asStateFlow()

    private val _selectedExercise = MutableStateFlow<WorkoutExercise?>(null)
    val selectedExercise = _selectedExercise.asStateFlow()

    fun onSelectExercise(exerciseId: Long){
        viewModelScope.launch(Dispatchers.IO){
            useCases.getWorkoutExerciseByIdUseCase(exerciseId).collect { exercise ->
                exercise.let {
                    _selectedExercise.value = it
                }
            }
        }
    }

    fun clearSelectedExercise(){
        _selectedExercise.value = null
        _sets.value = emptyList()
        viewModelScope.coroutineContext.cancelChildren() // so the set list is not fetched for every exercise
    }

    fun observeSets(exerciseId: Long){
        viewModelScope.launch(Dispatchers.IO){
            useCases.getSetsForExerciseUseCase(exerciseId).collectLatest {
                _sets.value = it
            }
        }
    }

    fun onAddSet(exerciseId: Long){
        viewModelScope.launch(Dispatchers.IO){
            useCases.insertSetUseCase(
                Set(
                    reps = 0,
                    weight = 0.0,
                    setNumber = 0,
                    workoutExerciseId = exerciseId
                )
            )
        }
    }

    fun onUpdateSet(set: Set){
        viewModelScope.launch(Dispatchers.IO){
            useCases.updateSetUseCase(set)
        }
    }

    fun onDeleteSet(set: Set){
        viewModelScope.launch(Dispatchers.IO){
            useCases.deleteSetUseCase(set)
        }
    }
}