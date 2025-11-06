package com.andriibryliant.gymbros.data.mapper

import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.data.local.entity.MuscleGroupEntity
import com.andriibryliant.gymbros.data.local.relation.ExerciseWithMuscleGroups
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.MuscleGroup

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

fun ExerciseWithMuscleGroups.toDomain(): Exercise =
    Exercise(
        id = exercise.exerciseId,
        name = exercise.name,
        iconResName = exercise.iconResName,
        muscleGroups = muscleGroups.map { entity ->
            MuscleGroup(
                id = entity.muscleGroupId,
                name = entity.name
            )
        }
    )

fun MuscleGroupEntity.toDomain(): MuscleGroup =
    MuscleGroup(
        id = muscleGroupId,
        name = name
    )

fun MuscleGroup.toEntity(): MuscleGroupEntity =
    MuscleGroupEntity(
        muscleGroupId = id,
        name = name
    )