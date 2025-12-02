package com.andriibryliant.gymbros.presentation.settings.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.andriibryliant.gymbros.domain.AppThemeMode

@Composable
fun ThemeToggleIcon(isDark: AppThemeMode) {
    val icon = when(isDark){
        AppThemeMode.DARK -> Icons.Default.DarkMode
        AppThemeMode.LIGHT -> Icons.Default.LightMode
        AppThemeMode.SYSTEM -> if (isSystemInDarkTheme()) Icons.Default.DarkMode else Icons.Default.LightMode
    }

    val transition = updateTransition(targetState = icon, label = "themeTransition")

    val scale by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = 300
                1f at 0
                0.6f at 80
                1.1f at 180
                1f at 300
            }
        },
        label = "scale"
    ) {
        1f
    }

    val alpha by transition.animateFloat(label = "alpha") {
        1f
    }

    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                alpha = alpha
            )
            .size(32.dp)
    )
}