package com.andriibryliant.gymbros.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val iconResName: String
)