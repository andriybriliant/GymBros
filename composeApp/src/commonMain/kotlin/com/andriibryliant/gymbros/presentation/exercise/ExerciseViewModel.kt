package com.andriibryliant.gymbros.presentation.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.StoredIconResName
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.DrawableResource

class ExerciseViewModel(
    useCases: ExerciseUseCases
): ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())

    val exercises = useCases.getAllExercisesUseCase()
        .flowOn(Dispatchers.IO)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _exercises.value
    )

    fun getExerciseIcon(exercise: Exercise): DrawableResource{
        return try {
            StoredIconResName.asResource(exercise.iconResName)
        }catch (e: Exception){
            Res.drawable.compose_multiplatform
        }
    }
}