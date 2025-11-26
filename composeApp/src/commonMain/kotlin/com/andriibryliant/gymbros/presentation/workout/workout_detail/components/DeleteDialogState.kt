package com.andriibryliant.gymbros.presentation.workout.workout_detail.components

import com.andriibryliant.gymbros.domain.model.WorkoutExercise

sealed interface DeleteDialogState {
    data object DeleteWorkout: DeleteDialogState
    data object CancelAddWorkout: DeleteDialogState
    data class DeleteWorkoutExercise(val exercise: WorkoutExercise): DeleteDialogState
    data object DeleteExercise: DeleteDialogState
}