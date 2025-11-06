package com.andriibryliant.gymbros

import androidx.compose.ui.window.ComposeUIViewController
import com.andriibryliant.gymbros.di.initKoin
import com.andriibryliant.gymbros.presentation.app.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }