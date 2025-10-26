package com.andriibryliant.gymbros.domain.model

data class WorkoutExercise(
    val id: Long = 0,
    val workoutId: Long,
    val exercise: Exercise,
    val orderInWorkout: Int,
    val sets: List<Set> = emptyList()
)