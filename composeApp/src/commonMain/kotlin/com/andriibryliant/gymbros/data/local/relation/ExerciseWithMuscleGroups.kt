package com.andriibryliant.gymbros.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.data.local.entity.ExerciseMuscleGroupCrossRef
import com.andriibryliant.gymbros.data.local.entity.MuscleGroupEntity

data class ExerciseWithMuscleGroups(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "muscleGroupId",
        associateBy = Junction(ExerciseMuscleGroupCrossRef::class)
    )
    val muscleGroups: List<MuscleGroupEntity>
)
