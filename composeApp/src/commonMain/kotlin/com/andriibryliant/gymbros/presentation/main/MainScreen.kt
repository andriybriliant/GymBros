package com.andriibryliant.gymbros.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.exercise_list.ExerciseListScreen
import com.andriibryliant.gymbros.presentation.main.components.DateRangePickerDialog
import com.andriibryliant.gymbros.presentation.main.components.TopBarWithFilter
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel
import com.andriibryliant.gymbros.presentation.workout.workout_list.WorkoutListScreen
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.exercises
import gymbros.composeapp.generated.resources.filter_workouts
import gymbros.composeapp.generated.resources.workouts
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel,
    mainScreenViewModel: MainScreenViewModel,
    onSettingsClick: () -> Unit,
    onWorkoutClick: (Workout) -> Unit,
    onExerciseClick: (Exercise) -> Unit,
    onAddWorkoutClick: () -> Unit,
    onAddExerciseClick: () -> Unit
){
    var showDatePicker by remember { mutableStateOf(false) }
    val selectedTab by mainScreenViewModel.selectedTabIndex.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarWithFilter(
                title = stringResource(Res.string.filter_workouts),
                onSettingsClick = onSettingsClick,
                onFilterBarClick = {
                    showDatePicker = true
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    when(selectedTab){
                        0 -> onAddWorkoutClick()
                        1 -> onAddExerciseClick()
                    }
                }
            ){
                Icon(
                    Icons.Default.Add,
                    "Add workout button"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            PrimaryTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier
                    .height(50.dp),
                divider = {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceTint)
                }
            ){
                Tab(
                    selected = selectedTab == 0,
                    onClick = {
                        mainScreenViewModel.onSelectedTabChange(0)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 25.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .height(50.dp),
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ){
                    Text(
                        text = stringResource(Res.string.workouts),
                        fontWeight = FontWeight.Medium
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        mainScreenViewModel.onSelectedTabChange(1)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 25.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .height(50.dp),
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ){
                    Text(
                        text = stringResource(Res.string.exercises),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if(selectedTab == 0){
                WorkoutListScreen(
                    viewModel = workoutViewModel,
                    onWorkoutClick = onWorkoutClick
                )
            }else if(selectedTab == 1){
                ExerciseListScreen(
                    viewModel = exerciseViewModel,
                    onExerciseClick = onExerciseClick
                )
            }

        }
    }

    if (showDatePicker){
        DateRangePickerDialog(
            onDismiss = { showDatePicker = false },
            onConfirm = {start, end ->
                showDatePicker = false
            }
        )
    }
}