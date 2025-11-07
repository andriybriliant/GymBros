package com.andriibryliant.gymbros.presentation.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.exercise_detail.ExerciseDetailScreen
import com.andriibryliant.gymbros.presentation.exercise.exercise_detail.ExerciseDetailViewModel
import com.andriibryliant.gymbros.presentation.exercise.choose_icon.ChooseExerciseIconScreen
import com.andriibryliant.gymbros.presentation.main.MainScreen
import com.andriibryliant.gymbros.presentation.main.MainScreenViewModel
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
                    val mainScreenViewModel = koinViewModel<MainScreenViewModel>()

                    MainScreen(
                        workoutViewModel = workoutViewModel,
                        exerciseViewModel = exerciseViewModel,
                        mainScreenViewModel = mainScreenViewModel,
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
                    AddWorkoutScreen(
                        onBackClick = {navController.navigateUp()}
                    )
                }
                composable<Route.AddExercise>(
                    popExitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally() },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    popEnterTransition = { slideInHorizontally() }
                ){
                    ExerciseDetailScreen(
                        onChooseIconClick = { navController.navigate(Route.ChooseExerciseIcon) },
                        onBackClick = { navController.navigateUp() },
                        onExerciseSave = {
                            navController.navigateUp()
                        }
                    )
                }
                composable<Route.ExerciseDetail>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){ backstack ->
                    val detail: Route.ExerciseDetail = backstack.toRoute()
                    val exerciseId: Long = detail.id
                    val viewModel: ExerciseDetailViewModel = koinViewModel()

                    LaunchedEffect(exerciseId){
                        viewModel.onSelectExercise(exerciseId)
                    }

                    ExerciseDetailScreen(
                        viewModel = viewModel,
                        onChooseIconClick = { navController.navigate(Route.ChooseExerciseIcon) },
                        onBackClick = { navController.navigateUp() },
                        onExerciseSave = { navController.navigateUp() }
                    )
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
                    ChooseExerciseIconScreen(
                        onBackClick = { navController.navigateUp() }
                    )
                }
                composable<Route.Settings>(
                    exitTransition = { slideOutHorizontally{ initialOffset ->
                        initialOffset
                    } },
                    enterTransition = { slideInHorizontally{ initialOffset ->
                        initialOffset
                    } }
                ){
                    SettingsScreen(
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}