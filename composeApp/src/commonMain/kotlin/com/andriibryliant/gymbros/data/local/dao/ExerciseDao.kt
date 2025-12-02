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
import com.andriibryliant.gymbros.data.local.entity.MuscleGroupEntity
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMuscleGroups(groups: List<MuscleGroupEntity>)

    @Query("SELECT * FROM exercises WHERE exerciseId = :exerciseId")
    fun getExerciseById(exerciseId: Long): Flow<ExerciseWithMuscleGroups?>

    @Query("SELECT * FROM muscle_groups ORDER BY name")
    fun getAllMuscleGroups(): Flow<List<MuscleGroupEntity>>

    @Delete
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossRefs: List<ExerciseMuscleGroupCrossRef>)

    @Query("DELETE FROM exercisemusclegroupcrossref WHERE exerciseId = :exerciseId")
    suspend fun deleteCrossRefsForExercise(exerciseId: Long)

    @Transaction
    suspend fun updateExerciseWithMuscles(
        exercise: ExerciseEntity,
        muscleGroups: List<MuscleGroupEntity>
    ) {
        updateExercise(exercise)
        deleteCrossRefsForExercise(exercise.exerciseId)
        val newRefs = muscleGroups.map {
            ExerciseMuscleGroupCrossRef(
                exerciseId = exercise.exerciseId,
                muscleGroupId = it.muscleGroupId
            )
        }
        insertCrossRefs(newRefs)
    }

    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Transaction
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercisesWithMuscleGroups(): Flow<List<ExerciseWithMuscleGroups>>

    @Transaction
    @Query("SELECT * FROM exercises e JOIN ExerciseMuscleGroupCrossRef m ON e.exerciseId = m.exerciseId WHERE m.muscleGroupId IN (:groups) GROUP BY e.exerciseId")
    fun getExercisesByMuscleGroups(groups: List<Long>): Flow<List<ExerciseWithMuscleGroups>>
}