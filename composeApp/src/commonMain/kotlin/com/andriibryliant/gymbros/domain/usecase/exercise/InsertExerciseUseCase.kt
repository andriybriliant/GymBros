package com.andriibryliant.gymbros.domain.usecase.exercise

import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.repository.ExerciseRepository

class InsertExerciseUseCase(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise){
        repository.insertExercise(exercise)
    }
}