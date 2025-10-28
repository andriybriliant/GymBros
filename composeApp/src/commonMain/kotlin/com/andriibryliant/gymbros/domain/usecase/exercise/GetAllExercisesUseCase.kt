package com.andriibryliant.gymbros.domain.usecase.exercise

import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class GetAllExercisesUseCase(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<Exercise>>{
        return repository.getAllExercises()
    }
}