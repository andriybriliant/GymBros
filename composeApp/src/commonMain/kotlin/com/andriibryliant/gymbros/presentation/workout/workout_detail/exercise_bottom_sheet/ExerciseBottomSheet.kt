package com.andriibryliant.gymbros.presentation.workout.workout_detail.exercise_bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andriibryliant.gymbros.domain.model.Set
import com.andriibryliant.gymbros.presentation.util.GetIconResource
import com.andriibryliant.gymbros.presentation.workout.workout_detail.WorkoutDetailViewModel
import com.andriibryliant.gymbros.presentation.workout.workout_detail.components.EditSetDialog
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.add_set
import gymbros.composeapp.generated.resources.sets
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseBottomSheet(
    viewModel: ExerciseBottomSheetViewModel = koinViewModel(),
    exerciseId: Long,
    onDismiss: () -> Unit,
    onExerciseClick: () -> Unit
){
    val selectedExercise by viewModel.selectedExercise.collectAsState()
    val sets by viewModel.sets.collectAsState()
    var selectedSet by remember { mutableStateOf<Set?>(null) }

    LaunchedEffect(exerciseId){
        viewModel.onSelectExercise(exerciseId)
//        viewModel.observeSets(exerciseId)
    }

    selectedExercise?.let { workoutExercise ->
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
                viewModel.clearSelectedExercise()
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ){
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                item {
                    ListItem(
                        headlineContent = {
                            Text(workoutExercise.exercise.name)
                        },
                        leadingContent = {
                            Icon(
                                painterResource(GetIconResource.get(workoutExercise.exercise.iconResName)),
                                null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable{
                                onExerciseClick()
                            }
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(stringResource(Res.string.sets))
                }
                items(sets){ set ->
                    ListItem(
                        headlineContent = {
                            Text("${set.reps} reps")
                        },
                        leadingContent = {
                            Text((sets.indexOf(set) + 1).toString())
                        },
                        trailingContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "${set.weight} kg",
                                    fontSize = 14.sp
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.onDeleteSet(set)
                                    },
                                ) {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        null
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable{
                                selectedSet = set
                            }
                    )
                }
                item{
                    ListItem(
                        headlineContent = { Text(stringResource(Res.string.add_set)) },
                        leadingContent = {
                            Icon(
                                Icons.Default.Add,
                                null
                            )
                        },
                        modifier = Modifier
                            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
                                RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                            .clickable{
                                viewModel.onAddSet(workoutExercise.id)
                            }
                        ,
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            }
        }
    }

    if (selectedSet != null) {
        EditSetDialog(
            selectedSet = selectedSet!!,
            onDismiss = { selectedSet = null },
            onSave = { updatedSet ->
                viewModel.onUpdateSet(updatedSet)
                selectedSet = null
            }
        )
    }
}
