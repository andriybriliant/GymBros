package com.andriibryliant.gymbros.data.repository

import com.andriibryliant.gymbros.data.local.AppDatabase
import com.andriibryliant.gymbros.data.mapper.toDomain
import com.andriibryliant.gymbros.data.mapper.toSetEntities
import com.andriibryliant.gymbros.data.mapper.toWorkoutExercisesEntities
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class DefaultWorkoutRepository(
    private val db: AppDatabase
): WorkoutRepository {
    override fun getWorkoutsByDate(date: LocalDate): Flow<List<Workout>> {
        return db.workoutDao.getWorkoutsByDate(date).map { list -> list.map { it.toDomain() } }
    }

    override fun getWorkoutsByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Workout>> {
        return db.workoutDao.getWorkoutsByDateRange(startDate, endDate).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getAllWorkouts(): Flow<List<Workout>> {
        return db.workoutDao.getAllWorkouts().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertWorkout(workout: Workout) {
        db.workoutDao.insertFullWorkout(workout,
            workout.toWorkoutExercisesEntities(),
            workout.toSetEntities()
        )
    }

    override suspend fun deleteWorkout(workoutId: Long) {
        db.workoutDao.deleteWorkout(workoutId)
    }
}