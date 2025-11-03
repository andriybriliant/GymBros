package com.andriibryliant.gymbros.presentation.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.add_exercise.AddExerciseScreen
import com.andriibryliant.gymbros.presentation.exercise.choose_icon.ChooseExerciseIconScreen
import com.andriibryliant.gymbros.presentation.exercise.exercise_detail.ExerciseDetailScreen
import com.andriibryliant.gymbros.presentation.main.MainScreen
import com.andriibryliant.gymbros.presentation.settings.SettingsScreen
import com.andriibryliant.gymbros.presentation.theme.AppTheme
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel
import com.andriibryliant.gymbros.presentation.workout.add_workout.AddWorkoutScreen
import com.andriibryliant.gymbros.presentation.workout.choose_exercise.ChooseExerciseScreen
import com.andriibryliant.gymbros.presentation.workout.workout_detail.WorkoutDetailScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(){
    AppTheme {
       val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.MainGraph
        ){
            navigation<Route.MainGraph>(
                startDestination = Route.Home
            ){
                composable<Route.Home>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ){
                    val workoutViewModel = koinViewModel<WorkoutViewModel>()
                    val exerciseViewModel = koinViewModel<ExerciseViewModel>()

                    MainScreen(
                        workoutViewModel = workoutViewModel,
                        exerciseViewModel = exerciseViewModel,
                        onSettingsClick = { navController.navigate(Route.Settings) },
                        onWorkoutClick = { workout ->
                            navController.navigate(Route.WorkoutDetail(workout.id))
                        },
                        onExerciseClick = { exercise ->
                            navController.navigate(Route.ExerciseDetail(exercise.id))
                        },
                        onAddWorkoutClick = { navController.navigate(Route.AddWorkout) },
                        onAddExerciseClick = { navController.navigate(Route.AddExercise) }
                    )
                }
                composable<Route.WorkoutDetail>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    WorkoutDetailScreen()
                }
                composable<Route.AddWorkout>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    AddWorkoutScreen()
                }
                composable<Route.AddExercise>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    AddExerciseScreen()
                }
                composable<Route.ExerciseDetail>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    ExerciseDetailScreen()
                }
                composable<Route.ChooseExercise>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    ChooseExerciseScreen()
                }
                composable<Route.ChooseExerciseIcon>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    ChooseExerciseIconScreen()
                }
                composable<Route.Settings>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    SettingsScreen()
                }

            }
        }
    }
}