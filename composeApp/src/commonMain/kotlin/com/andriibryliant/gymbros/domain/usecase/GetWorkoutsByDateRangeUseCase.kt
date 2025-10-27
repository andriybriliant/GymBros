package com.andriibryliant.gymbros.domain.usecase

import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetWorkoutsByDateRangeUseCase(
    private val repository: WorkoutRepository
) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<Workout>> {
        return repository.getWorkoutsByDateRange(startDate, endDate)
    }
}