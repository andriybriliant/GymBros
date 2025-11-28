package com.andriibryliant.gymbros.presentation.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun isDynamicColorAvailable(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

@Composable
actual fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme? {
    if(isDynamicColorAvailable()){
        return when(darkTheme){
            true -> dynamicDarkColorScheme(LocalContext.current)
            false -> dynamicLightColorScheme(LocalContext.current)
        }
    }
    return null
}