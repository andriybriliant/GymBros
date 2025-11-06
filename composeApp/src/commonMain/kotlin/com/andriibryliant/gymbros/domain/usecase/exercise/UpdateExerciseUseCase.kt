package com.andriibryliant.gymbros.domain.usecase.exercise

import com.andriibryliant.gymbros.data.repository.DefaultExerciseRepository
import com.andriibryliant.gymbros.domain.model.Exercise

class UpdateExerciseUseCase(
    private val repository: DefaultExerciseRepository
) {
    suspend operator fun invoke(exercise: Exercise){
        repository.updateExercise(exercise)
    }
}