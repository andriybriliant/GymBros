package com.andriibryliant.gymbros.data.repository

import com.andriibryliant.gymbros.data.local.AppDatabase
import com.andriibryliant.gymbros.data.local.entity.ExerciseMuscleGroupCrossRef
import com.andriibryliant.gymbros.data.mapper.toDomain
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultExerciseRepository(
    private val db: AppDatabase
): ExerciseRepository {
    override fun getAllExercises(): Flow<List<Exercise>> {
        return db.exerciseDao.getAllExercisesWithMuscleGroups().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertExercise(exercise: Exercise) {
        exercise.muscleGroups.forEach { muscleGroup ->
            db.exerciseDao.insertExerciseMuscleGroupCrossRef(
                ExerciseMuscleGroupCrossRef(
                    exercise.id,
                    muscleGroup.id
                )
            )
        }
    }

    override suspend fun deleteExercise(exerciseId: Long) {
        db.exerciseDao.deleteExercise(exerciseId)
    }
}