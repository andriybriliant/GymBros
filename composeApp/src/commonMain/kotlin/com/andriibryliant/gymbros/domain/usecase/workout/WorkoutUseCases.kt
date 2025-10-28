package com.andriibryliant.gymbros.domain.usecase.workout

data class WorkoutUseCases(
    val getWorkoutsByDateUseCase: GetWorkoutsByDateUseCase,
    val getWorkoutsByDateRangeUseCase: GetWorkoutsByDateRangeUseCase,
    val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
    val insertWorkoutUseCase: InsertWorkoutUseCase,
    val deleteWorkoutUseCase: DeleteWorkoutUseCase
)