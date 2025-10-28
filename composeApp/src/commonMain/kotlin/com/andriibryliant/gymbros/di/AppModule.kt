package com.andriibryliant.gymbros.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.andriibryliant.gymbros.data.local.AppDatabase
import com.andriibryliant.gymbros.data.local.DatabaseFactory
import com.andriibryliant.gymbros.data.repository.DefaultExerciseRepository
import com.andriibryliant.gymbros.data.repository.DefaultWorkoutRepository
import com.andriibryliant.gymbros.domain.repository.ExerciseRepository
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import com.andriibryliant.gymbros.domain.usecase.exercise.DeleteExerciseUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import com.andriibryliant.gymbros.domain.usecase.exercise.GetAllExercisesUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.InsertExerciseUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.DeleteWorkoutUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetAllWorkoutsUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetWorkoutsByDateRangeUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetWorkoutsByDateUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.InsertWorkoutUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    singleOf(::DefaultWorkoutRepository).bind<WorkoutRepository>()
    singleOf(::DefaultExerciseRepository).bind<ExerciseRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<AppDatabase>().workoutDao }
    single { get<AppDatabase>().exerciseDao }

    single{
        WorkoutUseCases(
            getWorkoutsByDateUseCase = GetWorkoutsByDateUseCase(get()),
            getWorkoutsByDateRangeUseCase = GetWorkoutsByDateRangeUseCase(get()),
            getAllWorkoutsUseCase = GetAllWorkoutsUseCase(get()),
            insertWorkoutUseCase = InsertWorkoutUseCase(get()),
            deleteWorkoutUseCase = DeleteWorkoutUseCase(get())
        )
    }

    single {
        ExerciseUseCases(
            getAllExercisesUseCase = GetAllExercisesUseCase(get()),
            insertExerciseUseCase = InsertExerciseUseCase(get()),
            deleteExerciseUseCase = DeleteExerciseUseCase(get())
        )
    }
}