package com.andriibryliant.gymbros.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercises")
data class WorkoutExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val exerciseId: Long,
    val workoutId: Long
)