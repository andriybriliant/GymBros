package com.andriibryliant.gymbros.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithFilter(
    title: String,
    onSettingsClick: () -> Unit,
    onFilterBarClick: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                        .clickable {
                            onFilterBarClick()
                        }
                        .padding(horizontal = 16.dp, vertical = 15.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Icon(
                            imageVector = Icons.Outlined.FilterAlt,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        modifier = Modifier.padding(vertical = 5.dp),
        navigationIcon = {
            IconButton(
                onClick = {}
            ){
                Icon(
                    Icons.Default.FitnessCenter,
                    "Logo"
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSettingsClick
            ){
                Icon(
                    Icons.Outlined.Settings,
                    "Settings"
                )
            }
        }
    )
}