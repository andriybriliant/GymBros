package com.andriibryliant.gymbros.presentation.exercise

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.MuscleGroup
import com.andriibryliant.gymbros.domain.model.StoredIconResName
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class ExerciseViewModel(
    private val useCases: ExerciseUseCases
): ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())

    val exercises = _exercises.asStateFlow()
    var observeExercisesJob: Job? = null
    private val _muscleGroups = useCases.getMuscleGroupsUseCase()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val muscleGroups = _muscleGroups

    var selectedMuscleGroupsIds = mutableStateListOf<Long>()
        private set

    fun getExerciseIcon(exercise: Exercise): DrawableResource{
        return try {
            StoredIconResName.asResource(exercise.iconResName)
        }catch (e: Exception){
            Res.drawable.compose_multiplatform
        }
    }

    fun fetchExercises(){
        observeExercisesJob?.cancel()
        observeExercisesJob = viewModelScope.launch(Dispatchers.IO) {
            useCases.getAllExercisesUseCase().collect {
                _exercises.value = it
            }
        }
    }

    fun onMuscleGroupToggle(group: MuscleGroup){
        if (group.id in selectedMuscleGroupsIds)
            selectedMuscleGroupsIds.remove(group.id)
        else
            selectedMuscleGroupsIds.add(group.id)
        getExercisesByMuscleGroups()
    }

    fun getExercisesByMuscleGroups(){
        if (selectedMuscleGroupsIds.isEmpty()){
            fetchExercises()
        }else{
            observeExercisesJob?.cancel()
            observeExercisesJob = viewModelScope.launch(Dispatchers.IO) {
                useCases.getExercisesByMuscleGroupsUseCase(selectedMuscleGroupsIds).collect {
                    _exercises.value = it
                }
            }
        }
    }
}