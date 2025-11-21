package com.andriibryliant.gymbros.presentation.exercise.exercise_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.MuscleGroup
import com.andriibryliant.gymbros.domain.model.StoredIconResName
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.compose_multiplatform
import gymbros.composeapp.generated.resources.no_name_specified
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource

class ExerciseDetailViewModel(
    private val useCases: ExerciseUseCases
) : ViewModel(){

    private val _muscleGroups = useCases.getMuscleGroupsUseCase()
        .flowOn(Dispatchers.IO)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedExercise = MutableStateFlow<Exercise?>(null)
    val selectedExercise = _selectedExercise.asStateFlow()

    val muscleGroups = _muscleGroups
    var exerciseName by mutableStateOf("")
        private set

    private var exerciseId by mutableStateOf<Long?>(null)

    var nameError by mutableStateOf<StringResource?>(null)
        private set

    private var selectedIconName by mutableStateOf("")

    @OptIn(InternalResourceApi::class)
    var selectedIcon by mutableStateOf(Res.drawable.compose_multiplatform)
        private set

    var selectedMuscleGroups = mutableStateListOf<MuscleGroup>()
        private set


    fun onSelectExercise(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getExerciseByIdUseCase(id).collect { exercise ->
                _selectedExercise.value = exercise
                exercise?.let { exercise ->
                    exerciseId = exercise.id
                    exerciseName = exercise.name
                    selectedIconName = exercise.iconResName
                    selectedMuscleGroups.addAll(exercise.muscleGroups)
                }
            }
        }
    }

    fun onExerciseNameChange(value: String){
        exerciseName = value
        nameError = null
    }

    fun onExerciseIconSelected(name: String){
        selectedIconName = name
        selectedIcon = try {
            StoredIconResName.asResource(storedName = selectedIconName)
        }catch (exception: Exception){
            Res.drawable.compose_multiplatform
        }
    }

    fun onMuscleGroupToggle(group: MuscleGroup){
        if (group in selectedMuscleGroups)
            selectedMuscleGroups.remove(group)
        else
            selectedMuscleGroups.add(group)
    }

    private fun validate(): Boolean {
        var isValid = true

        if (exerciseName.isBlank()){
            nameError = Res.string.no_name_specified
            isValid = false
        }

        return isValid
    }

    fun saveExercise(): Boolean{
        if (!validate()){
            return false
        }
        if (exerciseName.isNotBlank()){
            val exercise = Exercise(
                id = exerciseId ?: 0,
                name = exerciseName,
                iconResName = selectedIconName,
                muscleGroups = selectedMuscleGroups.toList()
            )

            if(exerciseId == null){
                viewModelScope.launch(Dispatchers.IO) {
                    useCases.insertExerciseUseCase(exercise)
                }
            }else{
                viewModelScope.launch(Dispatchers.IO) {
                    useCases.updateExerciseUseCase(exercise)
                }
            }

        }
        return true
    }

    fun onDeleteExercise(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.deleteExerciseUseCase(selectedExercise.value!!)
        }
    }
}