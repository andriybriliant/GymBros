package com.andriibryliant.gymbros.presentation.exercise.add_exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andriibryliant.gymbros.domain.model.StoredIconResName
import com.andriibryliant.gymbros.domain.model.StoredMuscleGroupString
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.add_exercise
import gymbros.composeapp.generated.resources.edit_exercise
import gymbros.composeapp.generated.resources.icon
import gymbros.composeapp.generated.resources.muscle_groups
import gymbros.composeapp.generated.resources.name
import gymbros.composeapp.generated.resources.save
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddExerciseScreen(
    viewModel: AddExerciseViewModel = koinViewModel(),
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
                Button(
                    onClick = {
                        if (viewModel.saveExercise()){
                            onExerciseSave()
                        }
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ){
                    Text(stringResource(Res.string.save))
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