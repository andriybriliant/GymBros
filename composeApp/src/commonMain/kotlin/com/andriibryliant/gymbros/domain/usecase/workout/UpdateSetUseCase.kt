package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository

class UpdateSetUseCase(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(set: Set){
        repository.updateSet(set)
    }
}