package com.andriibryliant.gymbros.presentation.workout.workout_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.cancel
import gymbros.composeapp.generated.resources.save
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate?) -> Unit
){
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDateMillis: Long? = datePickerState.selectedDateMillis
                    if(selectedDateMillis == null){
                        onConfirm(null)
                    }else{
                        val selectedDate = LocalDate.fromEpochDays(Instant.fromEpochMilliseconds(
                            selectedDateMillis
                        ).epochSeconds.seconds.inWholeDays)
                        onConfirm(selectedDate)
                    }
                }
            ){
                Text(stringResource(Res.string.save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ){
                Text(stringResource(Res.string.cancel))
            }
        }
    ){
        DatePicker(
            state = datePickerState,
            modifier = Modifier
                .padding(vertical = 15.dp)
        )
    }
}