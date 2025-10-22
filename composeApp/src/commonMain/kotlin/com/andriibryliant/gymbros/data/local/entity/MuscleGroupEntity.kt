package com.andriibryliant.gymbros.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups")
data class MuscleGroupEntity(
    @PrimaryKey(autoGenerate = true) val muscleGroupId: Long = 0L,
    val name: String
)
