package com.andriibryliant.gymbros.domain.usecase.exercise

import com.andriibryliant.gymbros.data.repository.DefaultExerciseRepository
import com.andriibryliant.gymbros.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

class GetExerciseByIdUseCase(
    private val repository: DefaultExerciseRepository
) {
    operator fun invoke(exerciseId: Long): Flow<Exercise?>{
        return repository.getExerciseById(exerciseId)
    }
}