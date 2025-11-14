package com.andriibryliant.gymbros.data.mapper

import com.andriibryliant.gymbros.data.local.entity.SetEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutExerciseEntity
import com.andriibryliant.gymbros.data.local.relation.WorkoutExerciseWithExerciseAndSets
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
                        weight = setEntity.weight,
                        setNumber = setEntity.setNumber,
                        workoutExerciseId = setEntity.exerciseId
                    )
                },
                workoutId = exerciseWithExerciseAndSets.workoutExercise.workoutId,
                orderInWorkout = exerciseWithExerciseAndSets.workoutExercise.orderInWorkout
            )
        }
    )

fun Workout.toWorkoutExercisesEntities() : List<WorkoutExerciseEntity>{
    val exercises = this.exercises.map { exercise ->
        WorkoutExerciseEntity(
            workoutExerciseId = exercise.id,
            workoutId = exercise.workoutId,
            exerciseId = exercise.exercise.id,
            orderInWorkout = exercise.orderInWorkout
        )
    }

    return exercises
}

fun Workout.toSetEntities(): List<SetEntity>{
    val sets: List<SetEntity> = this.exercises.flatMap { exercise ->
        exercise.sets.map { set ->
            SetEntity(
                setId = set.id,
                exerciseId = exercise.id,
                setNumber = set.setNumber,
                reps = set.reps,
                weight = set.weight
            )
        }
    }

    return sets
}

fun WorkoutExerciseWithExerciseAndSets.toDomain(): WorkoutExercise = WorkoutExercise(
    id = workoutExercise.workoutExerciseId,
    workoutId = workoutExercise.workoutId,
    exercise = exercise.toDomain(),
    orderInWorkout = workoutExercise.orderInWorkout
)

fun WorkoutExercise.toEntity(): WorkoutExerciseEntity = WorkoutExerciseEntity(
    workoutExerciseId = id,
    workoutId = workoutId,
    exerciseId = exercise.id,
    orderInWorkout = orderInWorkout
)

fun Set.toEntity() = SetEntity(
    setId = id,
    exerciseId = workoutExerciseId,
    setNumber = setNumber,
    reps = reps,
    weight = weight
)

fun SetEntity.toDomain() = Set(
    id = setId,
    workoutExerciseId = exerciseId,
    setNumber = setNumber,
    reps = reps,
    weight = weight
)

