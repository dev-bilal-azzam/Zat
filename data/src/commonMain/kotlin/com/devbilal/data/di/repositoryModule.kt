package com.devbilal.data.di

import com.devbilal.data.repository.authentication.AuthenticationRepositoryImpl
import com.devbilal.data.repository.authentication.AuthenticationStrategyFactory
import com.devbilal.data.repository.diary.DiaryEntryRepositoryImpl
import com.devbilal.data.repository.diary.DiaryHistoryRepositoryImpl
import com.devbilal.data.repository.settings.SettingsRepositoryImpl
import com.devbilal.domain.repository.AuthenticationRepository
import com.devbilal.domain.repository.DiaryEntryRepository
import com.devbilal.domain.repository.DiaryHistoryRepository
import com.devbilal.domain.repository.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {
    single<SettingsRepository>(createdAtStart = true) { SettingsRepositoryImpl(settings = get()) }
    singleOf(::AuthenticationStrategyFactory)
    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(settingsStorage = get(), strategyFactory = get())
    }
    single<DiaryEntryRepository> { DiaryEntryRepositoryImpl(diaryEntryDao = get(), fileManager = get()) }
    single<DiaryHistoryRepository> { DiaryHistoryRepositoryImpl(diaryHistoryDao = get(), fileManager = get()) }
}
