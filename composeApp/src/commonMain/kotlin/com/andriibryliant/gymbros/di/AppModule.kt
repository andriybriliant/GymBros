package com.andriibryliant.gymbros.di

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.andriibryliant.gymbros.data.local.AppDatabase
import com.andriibryliant.gymbros.data.local.DatabaseFactory
import com.andriibryliant.gymbros.data.local.entity.MuscleGroupEntity
import com.andriibryliant.gymbros.data.repository.DefaultExerciseRepository
import com.andriibryliant.gymbros.data.repository.DefaultWorkoutRepository
import com.andriibryliant.gymbros.domain.repository.ExerciseRepository
import com.andriibryliant.gymbros.domain.repository.WorkoutRepository
import com.andriibryliant.gymbros.domain.usecase.exercise.DeleteExerciseUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import com.andriibryliant.gymbros.domain.usecase.exercise.GetAllExercisesUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.GetExerciseByIdUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.GetMuscleGroupsUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.InsertExerciseUseCase
import com.andriibryliant.gymbros.domain.usecase.exercise.UpdateExerciseUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.DeleteSetUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.DeleteWorkoutUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetAllWorkoutsUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetSetsForExerciseUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetWorkoutByIdUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetWorkoutsByDateRangeUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.GetWorkoutsByDateUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.InsertSetUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.InsertWorkoutUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.UpdateSetUseCase
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.exercise_detail.ExerciseDetailViewModel
import com.andriibryliant.gymbros.presentation.main.MainScreenViewModel
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    singleOf(::DefaultWorkoutRepository).bind<WorkoutRepository>()
    singleOf(::DefaultExerciseRepository).bind<ExerciseRepository>()

    single {
        get<DatabaseFactory>().create()
            .addCallback(object : RoomDatabase.Callback(){
                override fun onCreate(connection: SQLiteConnection) {
                    super.onCreate(connection)
                    CoroutineScope(Dispatchers.IO).launch {
                        val prepopulateMuscleGroups = listOf(
                            "chest", "back", "legs", "biceps", "triceps", "forearms", "abs"
                        ).map { MuscleGroupEntity(name = it) }
                        val db = get<AppDatabase>()
                        db.exerciseDao.insertAllMuscleGroups(prepopulateMuscleGroups)
                    }
                }
            })
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
            getWorkoutByIdUseCase = GetWorkoutByIdUseCase(get()),
            insertWorkoutUseCase = InsertWorkoutUseCase(get()),
            insertSetUseCase = InsertSetUseCase(get()),
            updateSetUseCase = UpdateSetUseCase(get()),
            getSetsForExerciseUseCase = GetSetsForExerciseUseCase(get()),
            deleteWorkoutUseCase = DeleteWorkoutUseCase(get()),
            deleteSetUseCase = DeleteSetUseCase(get())
        )
    }

    single {
        ExerciseUseCases(
            getAllExercisesUseCase = GetAllExercisesUseCase(get()),
            getMuscleGroupsUseCase = GetMuscleGroupsUseCase(get()),
            getExerciseByIdUseCase = GetExerciseByIdUseCase(get()),
            insertExerciseUseCase = InsertExerciseUseCase(get()),
            updateExerciseUseCase = UpdateExerciseUseCase(get()),
            deleteExerciseUseCase = DeleteExerciseUseCase(get())
        )
    }

    viewModelOf(::WorkoutViewModel)
    viewModelOf(::ExerciseViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::ExerciseDetailViewModel)
}