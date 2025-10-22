package com.andriibryliant.gymbros.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.andriibryliant.gymbros.data.local.entity.SetEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutExerciseEntity
import com.andriibryliant.gymbros.data.local.relation.WorkoutWithExercises
import kotlinx.datetime.LocalDate

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workoutEntity: WorkoutEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExerciseEntity: WorkoutExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(setEntity: SetEntity): Long

    @Transaction
    suspend fun insertFullWorkout(workout: WorkoutWithExercises){
        val workoutId = insertWorkout(workout.workout)
        workout.exercises.forEach { exerciseAndSets ->
            val exerciseId = insertWorkoutExercise(
                exerciseAndSets.workoutExercise.copy(workoutId = workoutId)
            )
            exerciseAndSets.sets.forEach { setEntity ->
                insertSet(
                    setEntity.copy(exerciseId = exerciseId)
                )
            }
        }
    }

    @Transaction
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    suspend fun getAllWorkouts(): List<WorkoutWithExercises>

    @Transaction
    @Query("SELECT * FROM workouts WHERE date = :date ")
    suspend fun getWorkoutsByDate(date: LocalDate): List<WorkoutWithExercises>

    @Transaction
    @Query("SELECT * FROM workouts WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getWorkoutsByDateRange(startDate: LocalDate, endDate: LocalDate): List<WorkoutWithExercises>

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
}