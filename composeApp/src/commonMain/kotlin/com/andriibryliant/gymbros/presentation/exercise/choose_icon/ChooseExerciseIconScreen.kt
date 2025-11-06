package com.andriibryliant.gymbros.presentation.exercise.choose_icon

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.choose_icon
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChooseExerciseIconScreen(
    onBackClick: () -> Unit
){
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(Res.string.choose_icon),
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

    }
}