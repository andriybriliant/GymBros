package com.andriibryliant.gymbros.presentation.workout.workout_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import com.andriibryliant.gymbros.presentation.util.GetIconResource
import com.andriibryliant.gymbros.presentation.workout.workout_detail.components.DateDialog
import com.andriibryliant.gymbros.presentation.workout.workout_detail.components.DeleteDialog
import com.andriibryliant.gymbros.presentation.workout.workout_detail.components.DeleteDialogState
import com.andriibryliant.gymbros.presentation.workout.workout_detail.exercise_bottom_sheet.ExerciseBottomSheet
import com.andriibryliant.gymbros.presentation.workout.workout_detail.exercise_bottom_sheet.ExerciseBottomSheetViewModel
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.add_exercise
import gymbros.composeapp.generated.resources.add_workout
import gymbros.composeapp.generated.resources.delete
import gymbros.composeapp.generated.resources.edit_workout
import gymbros.composeapp.generated.resources.name
import gymbros.composeapp.generated.resources.save
import gymbros.composeapp.generated.resources.sets
import gymbros.composeapp.generated.resources.workout
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WorkoutDetailScreen(
    viewModel: WorkoutDetailViewModel,
    bottomSheetViewModel: ExerciseBottomSheetViewModel,
    onAddExerciseClick: () -> Unit,
    onBackClick: () -> Unit
){
    val workoutName = viewModel.workoutName
    val nameError = viewModel.nameError
    val exercises by viewModel.exercises.collectAsState()
    val state = viewModel.state
    val deleteDialogState = viewModel.deleteDialogState
    val selectedExerciseId by viewModel.selectedExerciseId.collectAsState()
    val showSheet = selectedExerciseId != null
    var showDateDialog by remember { mutableStateOf(false) }
    val workoutDate = viewModel.workoutDate

    if(state == WorkoutDetailState.WorkoutDeleted){
        onBackClick()
    }

    BackHandler(true) {
        if(state == WorkoutDetailState.EditWorkout){
            viewModel.onSaveWorkout()
            onBackClick()
        }else if(state == WorkoutDetailState.AddWorkout){
            viewModel.setDialogState(DeleteDialogState.CancelAddWorkout)
        }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(
                    when(state){
                        WorkoutDetailState.EditWorkout -> Res.string.edit_workout
                        WorkoutDetailState.AddWorkout -> Res.string.add_workout
                        else -> Res.string.workout
                    }
                ),
                onBackClick = {
                    if(state == WorkoutDetailState.AddWorkout){
                        viewModel.setDialogState(DeleteDialogState.CancelAddWorkout)
                    }else{
                        if (viewModel.onSaveWorkout()){
                            onBackClick()
                        }
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer
            )
        },
        bottomBar = {
            Box{
                if(state == WorkoutDetailState.AddWorkout){
                    Button(
                        onClick = {
                            if (viewModel.onSaveWorkout()) {
                                onBackClick()
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(Res.string.save))
                    }
                }else if (state == WorkoutDetailState.EditWorkout){
                    OutlinedButton(
                        onClick = {
                            viewModel.setDialogState(DeleteDialogState.DeleteWorkout)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        border = BorderStroke(
                            2.dp, MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                    ){
                        Text(stringResource(Res.string.delete))
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ){
                item {
                    OutlinedTextField(
                        value = workoutName,
                        onValueChange = { newName ->
                            viewModel.onNameChange(newName)
                        },
                        label = {
                            Text(stringResource(Res.string.name))
                        },
                        isError = nameError != null,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    if (nameError != null){
                        Text(
                            text = stringResource(nameError),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
                item{
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                showDateDialog = true
                            }
                        ){
                            Icon(
                                Icons.Default.CalendarToday,
                                null
                            )
                        }
                        Text(workoutDate.toString())
                    }
                }
                items(exercises){ exercise ->
                    ListItem(
                        headlineContent = {
                            Text(exercise.exercise.name)
                        },
                        supportingContent = {
                            Text(exercise.sets.size.toString() + " " + stringResource(Res.string.sets))
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(GetIconResource.get(exercise.exercise.iconResName)),
                                null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        trailingContent = {
                            IconButton(
                                onClick = {
                                    viewModel.setDialogState(DeleteDialogState.DeleteWorkoutExercise(exercise))
//                                    viewModel.onDeleteExercise(exercise)
                                }
                            ){
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    null
                                )
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .clickable {
                                viewModel.onSelectExercise(exercise.id)
                            }
                    )
                }
                item {
                    ListItem(
                        headlineContent = { Text(stringResource(Res.string.add_exercise)) },
                        leadingContent = {
                            Icon(
                                Icons.Default.Add,
                                null
                            )
                        },
                        modifier = Modifier
                            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
                                RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .clickable{
                                viewModel.onSaveWorkout()
                                onAddExerciseClick()
                            },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    )
                }
            }
        }
        if(showSheet){
            ExerciseBottomSheet(
                viewModel = bottomSheetViewModel,
                onDismiss = {
                    viewModel.clearSelectedExercise()
                },
                exerciseId = selectedExerciseId?: 0,
                onExerciseClick = {
                    viewModel.onSaveWorkout()
                    onAddExerciseClick()
                }
            )
        }
        if(deleteDialogState != null){
            DeleteDialog(
                state = deleteDialogState,
                onDismiss = { viewModel.clearDialogState() },
                onConfirm = {
                    when(deleteDialogState){
                        DeleteDialogState.DeleteWorkout -> viewModel.onDeleteWorkout()
                        DeleteDialogState.CancelAddWorkout -> viewModel.onDeleteWorkout()
                        is DeleteDialogState.DeleteWorkoutExercise -> viewModel.onDeleteExercise(deleteDialogState.exercise)
                        else -> {}
                    }
                    viewModel.clearDialogState()
                }
            )
        }
        if(showDateDialog){
            DateDialog(
                onDismiss = {
                    showDateDialog = false
                },
                onConfirm = { date ->
                    viewModel.updateWorkoutDate(date)
                    showDateDialog = false
                }
            )
        }
    }

}