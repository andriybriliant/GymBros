package com.andriibryliant.gymbros.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutExerciseEntity

data class WorkoutWithExercises(
    @Embedded val workout: WorkoutEntity,

    @Relation(
        entity = WorkoutExerciseEntity::class,
        parentColumn = "workoutId",
        entityColumn = "workoutId"
    )
    val exercises: List<WorkoutExerciseWithExerciseAndSets>
)
