package com.andriibryliant.gymbros.domain.repository

import com.andriibryliant.gymbros.domain.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WorkoutRepository {
    fun getWorkoutsByDate(date: LocalDate): Flow<List<Workout>>
    fun getWorkoutsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Workout>>
    fun getAllWorkouts(): Flow<List<Workout>>
    suspend fun upsertWorkout(workout: Workout)
    suspend fun deleteWorkout(workoutId: Long)
}