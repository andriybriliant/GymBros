package com.andriibryliant.gymbros.data.repository

import com.andriibryliant.gymbros.data.local.AppDatabase
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.mapper.toDomain
import com.andriibryliant.gymbros.data.mapper.toEntity
import com.andriibryliant.gymbros.data.mapper.toSetEntities
import com.andriibryliant.gymbros.data.mapper.toWorkoutExercisesEntities
import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
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

    override fun getWorkoutById(id: Long): Flow<Workout?> {
        return db.workoutDao.getWorkoutById(id).map { workout ->
            workout?.toDomain()
        }
    }

    override fun getWorkoutExerciseById(id: Long): Flow<WorkoutExercise?> {
        return db.workoutDao.getWorkoutExerciseById(id).map { exercise ->
            exercise.toDomain()
        }
    }

    override suspend fun insertWorkout(workout: Workout): Long {
        return db.workoutDao.insertFullWorkout(workout,
            workout.exercises,
        )
    }

    override suspend fun insertWorkoutExercise(exercise: WorkoutExercise): Long {
        return db.workoutDao.insertWorkoutExercise(exercise.toEntity())
    }

    override suspend fun insertSet(set: Set) {
        db.workoutDao.insertSet(set.toEntity())
    }

    override suspend fun updateSet(set: Set) {
        db.workoutDao.updateSet(set.toEntity())
    }

    override suspend fun updateWorkoutExercise(exercise: WorkoutExercise) {
        db.workoutDao.updateWorkoutExercise(exercise.toEntity())
    }

    override fun getExerciseForWorkout(workoutId: Long): Flow<List<WorkoutExercise>> {
        return db.workoutDao.getWorkoutExercisesForWorkout(workoutId).map { exercises ->
            exercises.map { it.toDomain() }
        }
    }

    override fun getSetsForExercise(exerciseId: Long): Flow<List<Set>> {
        return db.workoutDao.getSetsForExercise(exerciseId).map { sets ->
            sets.map { it.toDomain() }
        }
    }

    override suspend fun deleteWorkout(workoutId: Long) {
        db.workoutDao.deleteWorkout(workoutId)
    }

    override suspend fun deleteWorkoutExercise(exercise: WorkoutExercise) {
        db.workoutDao.deleteWorkoutExercise(exercise.toEntity())
    }

    override suspend fun deleteSet(set: Set) {
        db.workoutDao.deleteSet(set.id)
    }
}