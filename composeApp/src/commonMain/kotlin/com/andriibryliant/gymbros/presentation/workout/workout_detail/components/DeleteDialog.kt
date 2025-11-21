package com.andriibryliant.gymbros.presentation.workout.workout_detail.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.cancel
import gymbros.composeapp.generated.resources.delete
import gymbros.composeapp.generated.resources.delete_exercise_prompt
import gymbros.composeapp.generated.resources.delete_exercise_title
import gymbros.composeapp.generated.resources.delete_workout_prompt
import gymbros.composeapp.generated.resources.delete_workout_title
import gymbros.composeapp.generated.resources.discard_changes_prompt
import gymbros.composeapp.generated.resources.discard_changes_title
import gymbros.composeapp.generated.resources.discard_confirm
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialog(
    state: DeleteDialogState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {

                Text(stringResource(
                    when(state){
                        DeleteDialogState.CancelAddWorkout -> Res.string.discard_confirm
                        else -> Res.string.delete
                    }
                ))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(Res.string.cancel)) }
        },
        title = { Text(stringResource(
            when(state){
                DeleteDialogState.DeleteWorkout -> Res.string.delete_workout_title
                DeleteDialogState.CancelAddWorkout -> Res.string.discard_changes_title
                is DeleteDialogState.DeleteWorkoutExercise -> Res.string.delete_exercise_title
            }
        )) },
        text = {
            Text(stringResource(
                when(state){
                    DeleteDialogState.DeleteWorkout -> Res.string.delete_workout_prompt
                    DeleteDialogState.CancelAddWorkout -> Res.string.discard_changes_prompt
                    is DeleteDialogState.DeleteWorkoutExercise -> Res.string.delete_exercise_prompt
                }
            ))
        }
    )
}