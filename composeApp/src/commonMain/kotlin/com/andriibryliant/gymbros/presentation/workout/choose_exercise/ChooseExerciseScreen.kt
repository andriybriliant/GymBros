package com.andriibryliant.gymbros.presentation.workout.choose_exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.presentation.exercise.ExerciseViewModel
import com.andriibryliant.gymbros.presentation.exercise.exercise_list.ExerciseListScreen
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.choose_exercise
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChooseExerciseScreen(
    onExerciseClick: (Exercise) -> Unit,
    onBackClick: () -> Unit
){
    BackHandler { onBackClick() }
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(Res.string.choose_exercise),
                onBackClick = onBackClick,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            ExerciseListScreen(
                viewModel = koinViewModel<ExerciseViewModel>(),
                onExerciseClick = { exercise ->
                    onExerciseClick(exercise)
                }
            )
        }
    }
}