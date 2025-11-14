package com.andriibryliant.gymbros.presentation.main.components

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    onBackClick: () -> Unit,
    title: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surface
){
    CenterAlignedTopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ){
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    "Navigate Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
    )
}