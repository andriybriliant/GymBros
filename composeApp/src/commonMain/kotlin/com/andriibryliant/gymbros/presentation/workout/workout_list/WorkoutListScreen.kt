package com.andriibryliant.gymbros.presentation.workout.workout_list

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.presentation.workout.WorkoutViewModel

@Composable
fun WorkoutListScreen(
    viewModel: WorkoutViewModel,
    onWorkoutClick: (Workout) -> Unit
){
    val workouts by viewModel.workouts.collectAsState()

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
            items(workouts){ workout ->
                ListItem(
                    headlineContent = {
                        Text(workout.name)
                    },
                    supportingContent = {
                        Text(workout.date.toString())
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            null
                        )
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable(
                            onClick = dropUnlessResumed {
                                onWorkoutClick(workout)
                            }
                        )
                        .animateItem(
                            fadeInSpec = tween(300, easing = LinearOutSlowInEasing),
                            fadeOutSpec = tween(300, easing = LinearOutSlowInEasing)
                        )
                )
            }
        }
    }
}