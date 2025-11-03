package com.andriibryliant.gymbros.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andriibryliant.gymbros.data.local.entity.SetEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutExerciseEntity
import com.andriibryliant.gymbros.data.local.relation.WorkoutWithExercises
import com.andriibryliant.gymbros.data.mapper.toEntity
import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workoutEntity: WorkoutEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExerciseEntity: WorkoutExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(setEntity: SetEntity): Long

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExerciseEntity)

    @Update
    suspend fun updateSet(setEntity: SetEntity)

    @Transaction
    suspend fun insertFullWorkout(
        workout: Workout,
        workoutExercises: List<WorkoutExerciseEntity>,
        workoutSets: List<SetEntity>
    ){
        val workoutId = if(workout.id == 0L) insertWorkout(workout.toEntity()) else{
            updateWorkout(workout.toEntity())
            workout.id
        }

        workoutExercises.forEach { entity ->
            val exerciseId = if(entity.workoutExerciseId == 0L) insertWorkoutExercise(entity.copy(workoutId = workoutId)) else {
                updateWorkoutExercise(entity.copy(workoutId = workoutId))
                entity.workoutExerciseId
            }

            workoutSets.forEach { set ->
                val setId = if(set.setId == 0L) insertSet(set.copy(exerciseId = exerciseId)) else{
                    updateSet(set.copy(exerciseId = exerciseId))
                    set.setId
                }
            }
        }
    }

    @Transaction
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<WorkoutWithExercises>>

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutId = :id")
    fun getWorkoutById(id: Long): Flow<WorkoutWithExercises?>

    @Transaction
    @Query("SELECT * FROM workouts WHERE date = :date ")
    fun getWorkoutsByDate(date: LocalDate): Flow<List<WorkoutWithExercises>>

    @Transaction
    @Query("SELECT * FROM workouts WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getWorkoutsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<WorkoutWithExercises>>

    @Query("SELECT * FROM sets WHERE exerciseId = :id")
    fun getSetsForExercise(id: Long): Flow<List<SetEntity>>

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

    @Query("DELETE FROM sets WHERE setId = :setId")
    suspend fun deleteSet(setId: Long)
}