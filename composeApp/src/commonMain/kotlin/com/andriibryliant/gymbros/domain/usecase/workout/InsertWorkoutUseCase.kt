package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository

class InsertWorkoutUseCase(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(workout: Workout){
        repository.insertWorkout(workout)
    }
}