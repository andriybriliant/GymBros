package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository

class DeleteWorkoutUseCase(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(workoutId: Long){
        repository.deleteWorkout(workoutId)
    }
}