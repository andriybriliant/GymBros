package com.andriibryliant.gymbros.presentation.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate, LocalDate) -> Unit
){
    val datePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val start = LocalDate.fromEpochDays(Instant.fromEpochMilliseconds(datePickerState.selectedStartDateMillis?: 0).epochSeconds.seconds.inWholeDays)
                    val end = LocalDate.fromEpochDays(Instant.fromEpochMilliseconds(datePickerState.selectedEndDateMillis?: 0).epochSeconds.seconds.inWholeDays)
                    onConfirm(start, end)
                }
            ){
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ){
                Text("Cancel")
            }
        },
        modifier = Modifier
    ){
        DateRangePicker(
            state = datePickerState,
            modifier = Modifier
                .padding(vertical = 15.dp)
        )
    }
}