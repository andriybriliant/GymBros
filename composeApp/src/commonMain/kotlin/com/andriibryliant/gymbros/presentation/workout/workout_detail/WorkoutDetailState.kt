package com.andriibryliant.gymbros.presentation.workout.workout_detail

sealed interface WorkoutDetailState {
    data object AddWorkout: WorkoutDetailState
    data object EditWorkout: WorkoutDetailState
    data object WorkoutDeleted: WorkoutDetailState
}