package com.andriibryliant.gymbros.data.mapper

import com.andriibryliant.gymbros.data.local.entity.SetEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.local.relation.WorkoutWithExercises
import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.model.WorkoutExercise

fun WorkoutEntity.toDomain(): Workout =
    Workout(
        id = workoutId,
        date = date,
        name = name
    )

fun Workout.toEntity(): WorkoutEntity =
    WorkoutEntity(
        workoutId = id,
        date = date,
        name = name
    )

fun WorkoutWithExercises.toDomain(): Workout =
    Workout(
        id = workout.workoutId,
        date = workout.date,
        name = workout.name,
        exercises = exercises.map { exerciseWithExerciseAndSets ->
            WorkoutExercise(
                id = exerciseWithExerciseAndSets.workoutExercise.workoutExerciseId,
                exercise = exerciseWithExerciseAndSets.exercise.toDomain(),
                sets = exerciseWithExerciseAndSets.sets.map { setEntity ->
                    Set(
                        id = setEntity.setId,
                        reps = setEntity.reps,
                        weight = setEntity.weight
                    )
                }
            )
        }
    )
