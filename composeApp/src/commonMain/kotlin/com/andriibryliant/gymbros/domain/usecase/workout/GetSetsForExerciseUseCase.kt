package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class GetSetsForExerciseUseCase(
    private val repository: WorkoutRepository
) {
    operator fun invoke(exerciseId: Long): Flow<List<Set>>{
        return repository.getSetsForExercise(exerciseId)
    }
}