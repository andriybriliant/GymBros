package com.andriibryliant.gymbros.presentation.exercise.exercise_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andriibryliant.gymbros.domain.model.StoredMuscleGroupString
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.add_exercise
import gymbros.composeapp.generated.resources.delete
import gymbros.composeapp.generated.resources.edit_exercise
import gymbros.composeapp.generated.resources.icon
import gymbros.composeapp.generated.resources.muscle_groups
import gymbros.composeapp.generated.resources.name
import gymbros.composeapp.generated.resources.save
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseDetailViewModel = koinViewModel(),
    onChooseIconClick: () -> Unit,
    onBackClick: () -> Unit,
    onExerciseSave: () -> Unit
){
    val exerciseName = viewModel.exerciseName
    val nameError = viewModel.nameError
    val muscleGroups by viewModel.muscleGroups.collectAsStateWithLifecycle()
    val selectedMuscleGroups = viewModel.selectedMuscleGroups
    val selectedIcon = viewModel.selectedIcon

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = if(viewModel.selectedExercise.value != null){
                    stringResource(Res.string.edit_exercise)
                }else{
                    stringResource(Res.string.add_exercise)
                },
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Box {
                Row {
                    if(viewModel.selectedExercise.value != null){
                        OutlinedButton(
                            onClick = {
                                viewModel.onDeleteExercise()
                                onBackClick()
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.error
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ){
                            Text(stringResource(Res.string.delete))
                        }
                    }
                    Button(
                        onClick = {
                            if (viewModel.saveExercise()) {
                                onExerciseSave()
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(Res.string.save))
                    }
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = {
                    viewModel.onExerciseNameChange(it)
                },
                label = {
                    Text(stringResource(Res.string.name))
                },
                isError = nameError != null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (nameError != null){
               Text(
                   text = stringResource(nameError),
                   color = MaterialTheme.colorScheme.error,
                   style = MaterialTheme.typography.bodySmall
               )
            }
            Column{
                Text(
                    text = stringResource(Res.string.muscle_groups),
                    style = MaterialTheme.typography.labelLarge
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(muscleGroups){ group ->
                        FilterChip(
                            selected = group in selectedMuscleGroups,
                            onClick = {
                                viewModel.onMuscleGroupToggle(group)
                            },
                            label = {
                                Text(
                                    stringResource(StoredMuscleGroupString.asStringResource(group.name))
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        )
                    }
                }
            }
            Column {
                Text(
                    text = stringResource(Res.string.icon),
                    style = MaterialTheme.typography.labelLarge
                )
                IconButton(
                    onClick = onChooseIconClick,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .size(72.dp),
                    shape = RoundedCornerShape(10.dp)
                ){
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        painter = painterResource(selectedIcon),
                        contentDescription = null
                    )
                }
            }
        }
    }
}