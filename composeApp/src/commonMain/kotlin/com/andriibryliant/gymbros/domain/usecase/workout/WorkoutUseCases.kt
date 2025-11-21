package com.andriibryliant.gymbros.domain.usecase.workout

data class WorkoutUseCases(
    val getWorkoutsByDateUseCase: GetWorkoutsByDateUseCase,
    val getWorkoutsByDateRangeUseCase: GetWorkoutsByDateRangeUseCase,
    val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
    val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    val insertWorkoutUseCase: InsertWorkoutUseCase,
    val insertWorkoutExerciseUseCase: InsertWorkoutExerciseUseCase,
    val getExerciseForWorkoutUseCase: GetExerciseForWorkoutUseCase,
    val getWorkoutExerciseByIdUseCase: GetWorkoutExerciseByIdUseCase,
    val insertSetUseCase: InsertSetUseCase,
    val updateSetUseCase: UpdateSetUseCase,
    val updateWorkoutExerciseUseCase: UpdateWorkoutExerciseUseCase,
    val getSetsForExerciseUseCase: GetSetsForExerciseUseCase,
    val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    val deleteExerciseForWorkoutUseCase: DeleteExerciseForWorkoutUseCase,
    val deleteSetUseCase: DeleteSetUseCase
)