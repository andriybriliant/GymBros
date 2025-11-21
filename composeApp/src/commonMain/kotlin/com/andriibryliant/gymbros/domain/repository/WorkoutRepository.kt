package com.andriibryliant.gymbros.domain.repository

import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WorkoutRepository {
    fun getWorkoutsByDate(date: LocalDate): Flow<List<Workout>>
    fun getWorkoutsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Workout>>
    fun getAllWorkouts(): Flow<List<Workout>>
    fun getWorkoutById(id: Long): Flow<Workout?>
    fun getWorkoutExerciseById(id: Long): Flow<WorkoutExercise?>
    suspend fun insertWorkout(workout: Workout): Long
    suspend fun insertWorkoutExercise(exercise: WorkoutExercise): Long
    suspend fun insertSet(set: Set)
    suspend fun updateSet(set: Set)
    suspend fun updateWorkoutExercise(exercise: WorkoutExercise)
    fun getExerciseForWorkout(workoutId: Long): Flow<List<WorkoutExercise>>
    fun getSetsForExercise(exerciseId: Long): Flow<List<Set>>
    suspend fun deleteWorkout(workoutId: Long)
    suspend fun deleteWorkoutExercise(exercise: WorkoutExercise)
    suspend fun deleteSet(set: Set)
}