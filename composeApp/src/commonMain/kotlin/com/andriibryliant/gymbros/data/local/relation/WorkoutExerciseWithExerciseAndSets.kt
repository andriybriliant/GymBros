package com.andriibryliant.gymbros.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.data.local.entity.SetEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutExerciseEntity

data class WorkoutExerciseWithExerciseAndSets(
    @Embedded val workoutExercise: WorkoutExerciseEntity,

    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseId"
    )
    val exercise: ExerciseEntity,

    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseId"
    )
    val sets: List<SetEntity>
)
