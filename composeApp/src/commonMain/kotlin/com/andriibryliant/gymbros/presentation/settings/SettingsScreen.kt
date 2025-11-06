package com.andriibryliant.gymbros.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
){
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(Res.string.settings),
                onBackClick = onBackClick
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->

    }
}