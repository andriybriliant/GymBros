package com.andriibryliant.gymbros.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = [""])
data class ExerciseMuscleGroupCrossRef(
    val exerciseId: Long,
    val muscleGroupId: Long
)
