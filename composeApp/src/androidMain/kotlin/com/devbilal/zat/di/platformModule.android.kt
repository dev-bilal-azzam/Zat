package com.devbilal.zat.di

import com.devbilal.presentation.common.biometric.ActivityProvider
import com.devbilal.zat.AppLocalizer
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { ActivityProvider() }
    single<AppLocalizer>(
        createdAtStart = true
    ) {
        AppLocalizer(
            context = get(),
            settingsRepository = get()
        )
    }
}
