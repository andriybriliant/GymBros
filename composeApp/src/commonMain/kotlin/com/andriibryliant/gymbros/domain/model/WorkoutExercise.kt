package com.andriibryliant.gymbros.domain.model

data class WorkoutExercise(
    val id: Long = 0,
    val exercise: Exercise,
    val sets: List<Set> = emptyList()
)