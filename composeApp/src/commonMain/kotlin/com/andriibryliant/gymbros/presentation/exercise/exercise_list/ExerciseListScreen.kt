package com.andriibryliant.gymbros.presentation.exercise.exercise_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.StoredIconResName
import com.andriibryliant.gymbros.domain.model.StoredMuscleGroupString
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExerciseListScreen(
    viewModel: ExerciseViewModel,
    onExerciseClick: (Exercise) -> Unit
){
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp)
                .clip(RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(exercises){ exercise ->
                ListItem(
                    headlineContent = {
                        Text(exercise.name)
                    },
                    leadingContent = {
                        Icon(
                            painter = painterResource(viewModel.getExerciseIcon(exercise)),
                            null,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    supportingContent = {
                        Row {
                            exercise.muscleGroups.forEach { muscleGroup ->
                                Text(stringResource(StoredMuscleGroupString.asStringResource(muscleGroup.name)))
                                if (exercise.muscleGroups.indexOf(muscleGroup) != exercise.muscleGroups.size - 1){
                                    Text(", ")
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable(onClick = dropUnlessResumed {
                            onExerciseClick(exercise)
                        })
                )
            }
        }
    }
}