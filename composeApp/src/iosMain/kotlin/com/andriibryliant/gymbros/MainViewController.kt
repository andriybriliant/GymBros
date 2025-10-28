package com.andriibryliant.gymbros

import androidx.compose.ui.window.ComposeUIViewController
import com.andriibryliant.gymbros.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }