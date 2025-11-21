package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.data.repository.DefaultWorkoutRepository
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import kotlinx.coroutines.flow.Flow

class GetWorkoutExerciseByIdUseCase(
    private val repository: DefaultWorkoutRepository
) {
    operator fun invoke(exerciseId: Long): Flow<WorkoutExercise?>{
        return repository.getWorkoutExerciseById(exerciseId)
    }
}