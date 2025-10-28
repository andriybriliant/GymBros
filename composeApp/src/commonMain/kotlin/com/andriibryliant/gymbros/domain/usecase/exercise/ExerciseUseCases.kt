package com.andriibryliant.gymbros.domain.usecase.exercise

data class ExerciseUseCases(
    val getAllExercisesUseCase: GetAllExercisesUseCase,
    val insertExerciseUseCase: InsertExerciseUseCase,
    val deleteExerciseUseCase: DeleteExerciseUseCase
)