package com.andriibryliant.gymbros.presentation.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key.Companion.Settings
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.andriibryliant.gymbros.domain.AppThemeMode
import com.andriibryliant.gymbros.domain.localization.Language
import com.andriibryliant.gymbros.domain.localization.Localization
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.exercise_detail.ExerciseDetailScreen
import com.andriibryliant.gymbros.presentation.exercise.exercise_detail.ExerciseDetailViewModel
import com.andriibryliant.gymbros.presentation.exercise.choose_icon.ChooseExerciseIconScreen
import com.andriibryliant.gymbros.presentation.main.MainScreen
import com.andriibryliant.gymbros.presentation.main.MainScreenViewModel
import com.andriibryliant.gymbros.presentation.settings.SettingsScreen
import com.andriibryliant.gymbros.presentation.theme.AppTheme
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel
import com.andriibryliant.gymbros.presentation.workout.workout_detail.WorkoutDetailScreen
import com.andriibryliant.gymbros.presentation.workout.workout_detail.WorkoutDetailViewModel
import com.andriibryliant.gymbros.presentation.workout.choose_exercise.ChooseExerciseScreen
import com.andriibryliant.gymbros.presentation.workout.workout_detail.exercise_bottom_sheet.ExerciseBottomSheetViewModel
import com.russhwolf.settings.Settings
import dev.burnoo.compose.remembersetting.rememberStringSetting
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    dynamicTheme: ColorScheme? = null
){
    val appThemeController = AppThemeController()

    val dynamicColor by appThemeController.dynamicColor.collectAsState()

    var themeName by rememberStringSetting(
        key = "savedTheme",
        defaultValue = AppThemeMode.SYSTEM.name
    )

    val selectedTheme by derivedStateOf {
        AppThemeMode.valueOf(themeName)
    }
    AppTheme(
        themeMode = selectedTheme,
        dynamicColor = dynamicColor
    ) {
        val navController = rememberNavController()
        val localization = koinInject<Localization>()

        var languageIso by rememberStringSetting(
            key = "savedLanguageIso",
            defaultValue = Locale.current.language
        )

        val selectedLanguage by derivedStateOf {
            Language.entries.first { it.iso == languageIso }
        }

        localization.applyLanguage(languageIso)

        NavHost(
            navController = navController,
            startDestination = Route.MainGraph
        ) {
            navigation<Route.MainGraph>(
                startDestination = Route.Home
            ) {
                composable<Route.Home>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
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
                    popExitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = { slideOutHorizontally() },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    popEnterTransition = { slideInHorizontally() }
                ) { backstack ->
                    val detail: Route.WorkoutDetail = backstack.toRoute()
                    val workoutId: Long = detail.id

                    val viewModel = koinViewModel<WorkoutDetailViewModel>()
                    val bottomSheetViewModel = koinViewModel<ExerciseBottomSheetViewModel>()

                    LaunchedEffect(workoutId) {
                        viewModel.onEditWorkout(workoutId)
                    }

                    val savedStateHandle = navController.currentBackStackEntry
                        ?.savedStateHandle

                    LaunchedEffect(true) {
                        savedStateHandle?.getStateFlow<String?>("selectedExerciseId", "")
                            ?.collect { id ->
                                if (id!!.isNotBlank()) {
                                    viewModel.onAddExercise(id.toLong())
                                    savedStateHandle["selectedExerciseId"] = ""
                                }
                            }
                    }

                    WorkoutDetailScreen(
                        viewModel = viewModel,
                        bottomSheetViewModel = bottomSheetViewModel,
                        onBackClick = { navController.navigateUp() },
                        onAddExerciseClick = { navController.navigate(Route.ChooseExercise) }
                    )
                }
                composable<Route.AddWorkout>(
                    popExitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = { slideOutHorizontally() },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    val viewModel = koinViewModel<WorkoutDetailViewModel>()
                    val bottomSheetViewModel = koinViewModel<ExerciseBottomSheetViewModel>()

                    LaunchedEffect(true) {
                        viewModel.onAddWorkout()
                    }

                    val savedStateHandle = navController.currentBackStackEntry
                        ?.savedStateHandle

                    LaunchedEffect(true) {
                        savedStateHandle?.getStateFlow<String?>("selectedExerciseId", "")
                            ?.collect { id ->
                                if (id!!.isNotBlank()) {
                                    viewModel.onAddExercise(id.toLong())
                                    savedStateHandle["selectedExerciseId"] = ""
                                }
                            }
                    }

                    WorkoutDetailScreen(
                        viewModel = viewModel,
                        bottomSheetViewModel = bottomSheetViewModel,
                        onBackClick = { navController.navigateUp() },
                        onAddExerciseClick = { navController.navigate(Route.ChooseExercise) }
                    )
                }
                composable<Route.AddExercise>(
                    popExitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = { slideOutHorizontally() },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    popEnterTransition = { slideInHorizontally() }
                ) {

                    val viewModel: ExerciseDetailViewModel = koinViewModel()

                    val savedStateHandle = navController.currentBackStackEntry
                        ?.savedStateHandle

                    LaunchedEffect(true) {
                        savedStateHandle?.getStateFlow<String?>("selectedExerciseIcon", "")
                            ?.collect { name ->
                                if (name!!.isNotBlank()) {
                                    viewModel.onExerciseIconSelected(name)
                                    savedStateHandle["selectedExerciseIcon"] = ""
                                }
                            }
                    }

                    ExerciseDetailScreen(
                        viewModel = viewModel,
                        onChooseIconClick = { navController.navigate(Route.ChooseExerciseIcon) },
                        onBackClick = { navController.navigateUp() },
                        onExerciseSave = {
                            navController.navigateUp()
                        }
                    )
                }
                composable<Route.ExerciseDetail>(
                    popExitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = { slideOutHorizontally() },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    popEnterTransition = { slideInHorizontally() }
                ) { backstack ->
                    val detail: Route.ExerciseDetail = backstack.toRoute()
                    val exerciseId: Long = detail.id
                    val viewModel: ExerciseDetailViewModel = koinViewModel()

                    val savedStateHandle = navController.currentBackStackEntry
                        ?.savedStateHandle

                    LaunchedEffect(exerciseId) {
                        viewModel.onSelectExercise(exerciseId)
                    }

                    LaunchedEffect(true) {
                        savedStateHandle?.getStateFlow<String?>("selectedExerciseIcon", "")
                            ?.collect { name ->
                                if (name!!.isNotBlank()) {
                                    viewModel.onExerciseIconSelected(name)
                                    savedStateHandle["selectedExerciseIcon"] = ""
                                }
                            }
                    }

                    ExerciseDetailScreen(
                        viewModel = viewModel,
                        onChooseIconClick = { navController.navigate(Route.ChooseExerciseIcon) },
                        onBackClick = { navController.navigateUp() },
                        onExerciseSave = { navController.navigateUp() }
                    )
                }
                composable<Route.ChooseExercise>(
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    }
                ) {
                    ChooseExerciseScreen(
                        onBackClick = { navController.popBackStack() },
                        onExerciseClick = { exercise ->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("selectedExerciseId", exercise.id.toString())
                            navController.navigateUp()
                        }
                    )
                }
                composable<Route.ChooseExerciseIcon>(
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    }
                ) {
                    ChooseExerciseIconScreen(
                        onIconClicked = { name ->
                            navController
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("selectedExerciseIcon", name)
                            navController.navigateUp()
                        },
                        onBackClick = { navController.navigateUp() }
                    )
                }
                composable<Route.Settings>(
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    }
                ) {
                    SettingsScreen(
                        language = selectedLanguage,
                        theme = selectedTheme,
                        isDynamicColor = dynamicColor,
                        onBackClick = {
                            navController.navigateUp()
                        },
                        onLanguageChange = { language ->
                            languageIso = language
                            localization.applyLanguage(language)
                        },
                        onAppThemeChange = { themeMode ->
                            themeName = themeMode.name
                        },
                        onDynamicColorChange = { isDynamic ->
                            appThemeController.saveDynamicColor(isDynamic)
                        }
                    )
                }
            }
        }
    }
}