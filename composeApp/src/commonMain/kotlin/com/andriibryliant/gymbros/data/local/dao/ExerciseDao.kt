package com.andriibryliant.gymbros.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.data.local.entity.ExerciseMuscleGroupCrossRef
import com.andriibryliant.gymbros.data.local.relation.ExerciseWithMuscleGroups
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exerciseEntity: ExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseMuscleGroupCrossRef(exerciseMuscleGroupCrossRef: ExerciseMuscleGroupCrossRef): Long

    @Update
    suspend fun updateExercise(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exerciseId: Long)

    @Query("SELECT * FROM exercises ORDER BY name ASC")
    suspend fun getAllExercises(): List<ExerciseEntity>

    @Transaction
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercisesWithMuscleGroups(): Flow<List<ExerciseWithMuscleGroups>>
}