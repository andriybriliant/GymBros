package com.andriibryliant.gymbros.presentation.main

import androidx.compose.runtime.Composable
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel

@Composable
fun MainScreen(
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel,
    onSettingsClick: () -> Unit,
    onWorkoutClick: (Workout) -> Unit,
    onExerciseClick: (Exercise) -> Unit,
    onAddWorkoutClick: () -> Unit,
    onAddExerciseClick: () -> Unit
){

}