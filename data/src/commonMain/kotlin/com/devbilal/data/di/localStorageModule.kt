package com.devbilal.data.di

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.hash.Hasher
import com.devbilal.data.hash.getHasherInstance
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val localStorageModule = module {
    single { Json { ignoreUnknownKeys = true } }
    singleOf(::Settings)
    singleOf(::SettingsStorage)

    single<Hasher> { getHasherInstance() }
}