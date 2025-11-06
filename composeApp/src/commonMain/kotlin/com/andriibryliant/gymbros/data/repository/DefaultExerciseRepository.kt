package com.andriibryliant.gymbros.data.repository

import com.andriibryliant.gymbros.data.local.AppDatabase
import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.data.local.entity.ExerciseMuscleGroupCrossRef
import com.andriibryliant.gymbros.data.local.entity.MuscleGroupEntity
import com.andriibryliant.gymbros.data.mapper.toDomain
import com.andriibryliant.gymbros.data.mapper.toEntity
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.MuscleGroup
import com.andriibryliant.gymbros.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultExerciseRepository(
    private val db: AppDatabase
) : ExerciseRepository {
    override fun getAllExercises(): Flow<List<Exercise>> {
        return db.exerciseDao.getAllExercisesWithMuscleGroups().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getMuscleGroups(): Flow<List<MuscleGroup>> {
        return db.exerciseDao.getAllMuscleGroups().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getExerciseById(exerciseId: Long): Flow<Exercise?> {
        return db.exerciseDao.getExerciseById(exerciseId).map { exercise ->
            exercise?.toDomain()
        }
    }

    override suspend fun insertExercise(exercise: Exercise) {
        val exerciseId = db.exerciseDao.insertExercise(exercise.toEntity())
        exercise.muscleGroups.forEach { muscleGroup ->
            db.exerciseDao.insertExerciseMuscleGroupCrossRef(
                ExerciseMuscleGroupCrossRef(
                    exerciseId = exerciseId,
                    muscleGroupId = muscleGroup.id
                )
            )
        }
    }

    override suspend fun updateExercise(exercise: Exercise) {
        val muscleGroups: List<MuscleGroupEntity> = exercise.muscleGroups.map { muscleGroup ->
            muscleGroup.toEntity()
        }
        db.exerciseDao.updateExerciseWithMuscles(
            exercise = exercise.toEntity(),
            muscleGroups = muscleGroups
        )
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        db.exerciseDao.deleteExercise(exercise.toEntity())
    }
}