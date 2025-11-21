package com.andriibryliant.gymbros.presentation.workout.workout_detail.exercise_bottom_sheet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class ExerciseBottomSheetViewModel(
    private val useCases: WorkoutUseCases,
): ViewModel() {
    private val _sets = MutableStateFlow<List<Set>>(emptyList())
    var sets = _sets.asStateFlow()
    private val jobsToCancel = mutableStateListOf<Job>()
    private val _selectedExercise = MutableStateFlow<WorkoutExercise?>(null)
    val selectedExercise = _selectedExercise.asStateFlow()

    fun onSelectExercise(exerciseId: Long){
        val selectExerciseJob = viewModelScope.launch(Dispatchers.IO){
            useCases.getWorkoutExerciseByIdUseCase(exerciseId).collect { exercise ->
                exercise?.let {
                    _selectedExercise.value = it
                    observeSets(it.id)
                }
            }
        }
        jobsToCancel.add(selectExerciseJob)
    }

    fun clearSelectedExercise(){
        _selectedExercise.value = null
        _sets.value = emptyList()
        jobsToCancel.forEach { job ->
            job.cancel()
        }
        //viewModelScope.coroutineContext.cancelChildren() // so the set list is not fetched for every exercise
    }

    fun observeSets(exerciseId: Long){
        val observeSetsJob = viewModelScope.launch(Dispatchers.IO){
            useCases.getSetsForExerciseUseCase(exerciseId).collectLatest {
                _sets.value = it
            }
        }
        jobsToCancel.add(observeSetsJob)
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