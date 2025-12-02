package com.andriibryliant.gymbros.domain.repository

import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getAllExercises(): Flow<List<Exercise>>
    fun getMuscleGroups(): Flow<List<MuscleGroup>>
    fun getExerciseById(exerciseId: Long): Flow<Exercise?>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)
    fun getExercisesByMuscleGroups(groups: List<Long>): Flow<List<Exercise>>
}