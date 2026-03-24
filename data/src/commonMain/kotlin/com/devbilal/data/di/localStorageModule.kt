package com.devbilal.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.devbilal.data.datasource.local.database.ZatDatabase
import com.devbilal.data.datasource.local.database.diaryentry.DiaryEntryDao
import com.devbilal.data.datasource.local.database.diaryhistory.DiaryHistoryDao
import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.hash.Hasher
import com.devbilal.data.hash.getHasherInstance
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal val localStorageModule = module {
    // Settings
    single { Json { ignoreUnknownKeys = true } }
    singleOf(::Settings)
    singleOf(::SettingsStorage)

    // Hashing
    single<Hasher> { getHasherInstance() }

    //Database
    single { getDatabaseBuilder() }
    single<ZatDatabase> { getChatDatabase(get()) }
    single<DiaryEntryDao> { get<ZatDatabase>().getDiaryEntryDao() }
    single<DiaryHistoryDao> { get<ZatDatabase>().getDiaryHistoryDao() }
}

expect fun Scope.getDatabaseBuilder(): RoomDatabase.Builder<ZatDatabase>

private fun getChatDatabase(builder: RoomDatabase.Builder<ZatDatabase>): ZatDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()