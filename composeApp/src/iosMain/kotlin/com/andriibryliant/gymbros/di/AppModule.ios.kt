package com.andriibryliant.gymbros.di

import com.andriibryliant.gymbros.data.local.DatabaseFactory
import com.andriibryliant.gymbros.domain.localization.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { DatabaseFactory() }
        single<Localization> { Localization() }
    }