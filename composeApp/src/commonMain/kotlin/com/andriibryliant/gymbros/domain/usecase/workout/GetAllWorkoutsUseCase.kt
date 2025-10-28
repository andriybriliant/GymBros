package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class GetAllWorkoutsUseCase(
    private val repository: WorkoutRepository
) {
    operator fun invoke(): Flow<List<Workout>>{
        return repository.getAllWorkouts()
    }
}