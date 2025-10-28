package com.andriibryliant.gymbros

import android.app.Application
import com.andriibryliant.gymbros.di.initKoin
import org.koin.android.ext.koin.androidContext

class GymApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GymApplication)
        }
    }
}