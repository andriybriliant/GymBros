package com.andriibryliant.gymbros.domain.usecase.exercise

import com.andriibryliant.gymbros.domain.repository.ExerciseRepository

class DeleteExerciseUseCase(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exerciseId: Long){
        repository.deleteExercise(exerciseId)
    }
}