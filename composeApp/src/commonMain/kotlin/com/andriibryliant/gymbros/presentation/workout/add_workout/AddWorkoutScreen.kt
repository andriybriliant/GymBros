package com.andriibryliant.gymbros.presentation.workout.add_workout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.add_workout
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddWorkoutScreen(
    onBackClick: () -> Unit
){
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(Res.string.add_workout),
                onBackClick = onBackClick
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}