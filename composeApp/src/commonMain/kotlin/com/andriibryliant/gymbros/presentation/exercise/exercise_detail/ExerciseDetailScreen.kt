package com.andriibryliant.gymbros.presentation.exercise.exercise_detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.edit_exercise
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExerciseDetailScreen(
    onBackClick: () -> Unit
){
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(Res.string.edit_exercise),
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

    }
}