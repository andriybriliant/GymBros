package com.andriibryliant.gymbros.presentation.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MainGraph: Route

    @Serializable
    data object Home: Route

    @Serializable
    data class WorkoutDetail(val id: String): Route

    @Serializable
    data object AddWorkout: Route

    @Serializable
    data object ChooseExercise: Route

    @Serializable
    data class ExerciseDetail(val id: String): Route

    @Serializable
    data object AddExercise: Route

    @Serializable
    data object ChooseExerciseIcon: Route

    @Serializable
    data object Settings: Route
}