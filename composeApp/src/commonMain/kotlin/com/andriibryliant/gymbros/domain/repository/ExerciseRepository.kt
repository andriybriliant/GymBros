package com.andriibryliant.gymbros.domain.repository

import com.andriibryliant.gymbros.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getAllExercises(): Flow<List<Exercise>>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)
}