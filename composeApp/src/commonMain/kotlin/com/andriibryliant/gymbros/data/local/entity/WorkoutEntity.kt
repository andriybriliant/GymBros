package com.andriibryliant.gymbros.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val workoutId: Long = 0L,
    val date: LocalDate,
    val name: String
)