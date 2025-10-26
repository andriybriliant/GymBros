package com.andriibryliant.gymbros.domain.model

data class Set(
    val id: Long = 0,
    val reps: Int,
    val weight: Double,
    val setNumber: Int
)
