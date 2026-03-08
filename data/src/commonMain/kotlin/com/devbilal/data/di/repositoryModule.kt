package com.devbilal.data.di

import com.devbilal.data.repository.AuthenticationRepositoryImpl
import com.devbilal.data.repository.SettingsRepositoryImpl
import com.devbilal.domain.repository.AuthenticationRepository
import com.devbilal.domain.repository.SettingsRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<SettingsRepository>(createdAtStart = true) { SettingsRepositoryImpl(settings = get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(settingsStorage = get(), hasher = get()) }
}
