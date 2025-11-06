package com.andriibryliant.gymbros.domain.usecase.exercise

import com.andriibryliant.gymbros.data.repository.DefaultExerciseRepository
import com.andriibryliant.gymbros.domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

class GetMuscleGroupsUseCase(
    private val repository: DefaultExerciseRepository
) {
    operator fun invoke(): Flow<List<MuscleGroup>>{
        return repository.getMuscleGroups()
    }
}