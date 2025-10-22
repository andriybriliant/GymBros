package com.andriibryliant.gymbros.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class SetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val reps: Int,
    val weight: Double
)