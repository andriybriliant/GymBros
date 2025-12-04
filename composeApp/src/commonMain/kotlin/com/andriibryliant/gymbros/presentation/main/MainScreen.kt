package com.andriibryliant.gymbros.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.StoredMuscleGroupString
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.exercise_list.ExerciseListScreen
import com.andriibryliant.gymbros.presentation.main.components.DateRangePickerDialog
import com.andriibryliant.gymbros.presentation.main.components.TopBarWithFilter
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel
import com.andriibryliant.gymbros.presentation.workout.workout_list.WorkoutListScreen
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.exercises
import gymbros.composeapp.generated.resources.filter
import gymbros.composeapp.generated.resources.filter_exercises
import gymbros.composeapp.generated.resources.filter_workouts
import gymbros.composeapp.generated.resources.workouts
import kotlinx.datetime.LocalDate
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
    var showExerciseFilter by remember { mutableStateOf(false) }
    val selectedTab by mainScreenViewModel.selectedTabIndex.collectAsStateWithLifecycle()
    val startDate = workoutViewModel.startDateString
    val endDate = workoutViewModel.endDateString
    val muscleGroups by exerciseViewModel.muscleGroups.collectAsState()
    val selectedMuscleGroups = exerciseViewModel.selectedMuscleGroupsIds

    Scaffold(
        topBar = {
            TopBarWithFilter(
                title = when(selectedTab){
                    0 -> stringResource(Res.string.filter_workouts)
                    1 -> stringResource(Res.string.filter_exercises)
                    else -> stringResource(Res.string.filter)
                },
                onSettingsClick = onSettingsClick,
                onFilterBarClick = {
                    if(selectedTab == 0){
                        showDatePicker = true
                    }
                    if(selectedTab == 1){
                        showExerciseFilter = !showExerciseFilter
                        if(!showExerciseFilter){
                            exerciseViewModel.clearSelectedFilter()
                        }
                    }
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
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                ){
                    AnimatedVisibility(
                        visible = (startDate.isNotBlank()),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ){
                        FilterChip(
                            selected = true,
                            onClick = {},
                            label = {
                                if(endDate.isBlank()){
                                    Text(startDate)
                                }else{
                                    Text("$startDate - $endDate",
                                        textAlign = TextAlign.Center)
                                }
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    null,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable{
                                            workoutViewModel.observeWorkouts()
                                        }
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                    WorkoutListScreen(
                        viewModel = workoutViewModel,
                        onWorkoutClick = onWorkoutClick
                    )
                }
            }else if(selectedTab == 1){
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    AnimatedVisibility(
                        visible = showExerciseFilter,
                    ){
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
                        ) {
                            items(muscleGroups){ group ->
                                FilterChip(
                                    selected = group.id in selectedMuscleGroups,
                                    onClick = {
                                        exerciseViewModel.onMuscleGroupToggle(group)
                                    },
                                    label = {
                                        Text(
                                            stringResource(StoredMuscleGroupString.asStringResource(group.name))
                                        )
                                    },
                                    leadingIcon = {
                                        if (group.id in selectedMuscleGroups){
                                            Icon(
                                                Icons.Default.Check,
                                                null
                                            )
                                        }
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        labelColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                )
                            }
                        }
                    }
                    ExerciseListScreen(
                        viewModel = exerciseViewModel,
                        onExerciseClick = onExerciseClick
                    )
                }
            }
        }
    }

    if (showDatePicker){
        DateRangePickerDialog(
            onDismiss = { showDatePicker = false },
            onConfirm = {start, end ->
                workoutViewModel.onDateRangeSelected(start, end)
                showDatePicker = false
            }
        )
    }
}