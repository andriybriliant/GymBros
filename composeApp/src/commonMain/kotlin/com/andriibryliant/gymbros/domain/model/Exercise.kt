package com.andriibryliant.gymbros.domain.model

data class Exercise(
    val id: Long = 0,
    val name: String,
    val iconResName: String,
    val muscleGroups: List<MuscleGroup> = emptyList()
)
