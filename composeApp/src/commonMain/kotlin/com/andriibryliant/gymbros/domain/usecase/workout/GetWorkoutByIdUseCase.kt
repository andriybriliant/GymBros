package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.data.local.relation.WorkoutWithExercises
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class GetWorkoutByIdUseCase(
    private val repository: WorkoutRepository
) {
    operator fun invoke(id: Long): Flow<Workout?>{
        return repository.getWorkoutById(id)
    }
}