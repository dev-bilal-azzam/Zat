package com.devbilal.data.di

import com.devbilal.data.repository.SettingsRepositoryImpl
import com.devbilal.domain.repository.SettingsRepository
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    singleOf(::Settings)
    single<SettingsRepository>(createdAtStart = true) { SettingsRepositoryImpl(settings = get()) }

}