package com.andriibryliant.gymbros.domain.usecase.exercise

data class ExerciseUseCases(
    val getAllExercisesUseCase: GetAllExercisesUseCase,
    val getMuscleGroupsUseCase: GetMuscleGroupsUseCase,
    val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    val insertExerciseUseCase: InsertExerciseUseCase,
    val updateExerciseUseCase: UpdateExerciseUseCase,
    val deleteExerciseUseCase: DeleteExerciseUseCase
)