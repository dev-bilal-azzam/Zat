package com.devbilal.data.di

import com.devbilal.data.repository.authentication.AuthenticationRepositoryImpl
import com.devbilal.data.repository.SettingsRepositoryImpl
import com.devbilal.data.repository.authentication.AuthenticationStrategyFactory
import com.devbilal.domain.repository.AuthenticationRepository
import com.devbilal.domain.repository.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    single<SettingsRepository>(createdAtStart = true) { SettingsRepositoryImpl(settings = get()) }
    singleOf(::AuthenticationStrategyFactory)
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(settingsStorage = get(), strategyFactory = get()) }
}
