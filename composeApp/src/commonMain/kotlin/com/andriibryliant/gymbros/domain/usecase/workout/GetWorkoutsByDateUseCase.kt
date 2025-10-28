package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetWorkoutsByDateUseCase(
    private val repository: WorkoutRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Workout>> {
        return repository.getWorkoutsByDate(date)
    }
}