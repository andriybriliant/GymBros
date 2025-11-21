package com.andriibryliant.gymbros.presentation.workout.workout_detail

sealed interface WorkoutDetailState {
    data object AddExercise: WorkoutDetailState
    data object EditExercise: WorkoutDetailState
}