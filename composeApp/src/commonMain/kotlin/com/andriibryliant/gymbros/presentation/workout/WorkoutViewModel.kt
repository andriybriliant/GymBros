package com.andriibryliant.gymbros.presentation.workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class WorkoutViewModel(
    private val useCases: WorkoutUseCases
): ViewModel(){
    private var observeWorkoutsJob: Job? = null
    private var _workouts = MutableStateFlow<List<Workout>>(emptyList())
    var startDateString by mutableStateOf("")
        private set
    var endDateString by mutableStateOf("")
        private set

    val workouts = _workouts.asStateFlow()

    init {
        observeWorkouts()
    }

    fun observeWorkouts(){
        observeWorkoutsJob?.cancel()
        observeWorkoutsJob = viewModelScope.launch(Dispatchers.IO){
            startDateString = ""
            endDateString = ""
            useCases.getAllWorkoutsUseCase().collect {
                _workouts.value = it
            }
        }
    }

    fun onDateRangeSelected(startDate: LocalDate, endDate: LocalDate){
        val nullDate = LocalDate(1970, 1, 1)
        if(startDate == nullDate && endDate == nullDate){
            return
        }
        observeWorkoutsJob?.cancel()
        if (startDate == nullDate){
            observeWorkoutsJob = viewModelScope.launch {
                endDateString = endDate.toString()
                useCases.getWorkoutsByDateUseCase(endDate).collect {
                    _workouts.value = it
                }
            }
        }
        if (endDate == nullDate){
            observeWorkoutsJob = viewModelScope.launch(Dispatchers.IO){
                startDateString = startDate.toString()
                useCases.getWorkoutsByDateUseCase(startDate).collect {
                    _workouts.value = it
                }
            }
        }else{
            observeWorkoutsJob = viewModelScope.launch(Dispatchers.IO){
                startDateString = startDate.toString()
                endDateString = endDate.toString()
                useCases.getWorkoutsByDateRangeUseCase(startDate, endDate).collect {
                    _workouts.value = it
                }
            }
        }

    }
}