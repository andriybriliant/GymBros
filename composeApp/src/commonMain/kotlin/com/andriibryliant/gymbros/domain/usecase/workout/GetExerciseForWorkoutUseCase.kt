package com.andriibryliant.gymbros.domain.usecase.workout

import com.andriibryliant.gymbros.data.repository.DefaultWorkoutRepository
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import kotlinx.coroutines.flow.Flow

class GetExerciseForWorkoutUseCase(
    private val repository: DefaultWorkoutRepository
) {
    operator fun invoke(workoutId: Long): Flow<List<WorkoutExercise>>{
        return repository.getExerciseForWorkout(workoutId)
    }
}