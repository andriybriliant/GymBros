package com.andriibryliant.gymbros.presentation.workout.workout_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.andriibryliant.gymbros.domain.model.Set
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.cancel
import gymbros.composeapp.generated.resources.edit_set
import gymbros.composeapp.generated.resources.edit_set_weight
import gymbros.composeapp.generated.resources.reps
import gymbros.composeapp.generated.resources.save
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditSetDialog(
    selectedSet: Set,
    onDismiss: () -> Unit,
    onSave: (Set) -> Unit
){
    var reps by remember { mutableStateOf(selectedSet.reps.toString()) }
    var weight by remember { mutableStateOf(selectedSet.weight.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val updatedSet = selectedSet.copy(
                    reps = reps.toIntOrNull() ?: selectedSet.reps,
                    weight = weight.toDoubleOrNull() ?: selectedSet.weight
                )
                onSave(updatedSet)
            }) {
                Text(stringResource(Res.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(Res.string.cancel)) }
        },
        title = { Text(stringResource(Res.string.edit_set)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    label = { Text(stringResource(Res.string.reps)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(stringResource(Res.string.edit_set_weight)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )
            }
        }
    )
}