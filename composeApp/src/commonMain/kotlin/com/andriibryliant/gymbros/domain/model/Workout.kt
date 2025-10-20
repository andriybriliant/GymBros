package com.andriibryliant.gymbros.domain.model

import kotlinx.datetime.LocalDate

data class Workout(
    val id: Long = 0,
    val date: LocalDate,
    val name: String,
    val exercises: List<WorkoutExercise> = emptyList()
)
