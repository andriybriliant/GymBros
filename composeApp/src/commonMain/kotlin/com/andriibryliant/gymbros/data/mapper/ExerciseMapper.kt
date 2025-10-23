package com.andriibryliant.gymbros.data.mapper

import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.domain.model.Exercise

fun ExerciseEntity.toDomain(): Exercise =
    Exercise(
        id = exerciseId,
        name = name,
        iconResName = iconResName
    )

fun Exercise.toEntity(): ExerciseEntity =
    ExerciseEntity(
        exerciseId = id,
        name = name,
        iconResName = iconResName
    )