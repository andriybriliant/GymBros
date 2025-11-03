package com.andriibryliant.gymbros.domain.usecase.workout

data class WorkoutUseCases(
    val getWorkoutsByDateUseCase: GetWorkoutsByDateUseCase,
    val getWorkoutsByDateRangeUseCase: GetWorkoutsByDateRangeUseCase,
    val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
    val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    val insertWorkoutUseCase: InsertWorkoutUseCase,
    val insertSetUseCase: InsertSetUseCase,
    val updateSetUseCase: UpdateSetUseCase,
    val getSetsForExerciseUseCase: GetSetsForExerciseUseCase,
    val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    val deleteSetUseCase: DeleteSetUseCase
)