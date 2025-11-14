package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.data.repository.DefaultWorkoutRepository
import com.andriibryliant.gymbros.domain.model.WorkoutExercise

class DeleteExerciseForWorkoutUseCase(
    private val repository: DefaultWorkoutRepository
) {
    suspend operator fun invoke(exercise: WorkoutExercise){
        repository.deleteWorkoutExercise(exercise)
    }
}