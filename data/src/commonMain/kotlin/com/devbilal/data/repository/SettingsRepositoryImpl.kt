package com.devbilal.data.repository

import com.devbilal.data.datasource.local.setting.*
import com.devbilal.domain.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settings: SettingsStorage,
) : SettingsRepository {
    private val observableLanguage: MutableStateFlow<AppLanguage> =
        MutableStateFlow(settings.appLanguage)
    private val observableTheme: MutableStateFlow<AppTheme> = MutableStateFlow(settings.appTheme)

    override suspend fun applyLanguage(appLanguage: AppLanguage) {
        settings.appLanguage = appLanguage.also { observableLanguage.emit(appLanguage) }
    }

    override fun observeAppLanguage(): StateFlow<AppLanguage> {
        return observableLanguage
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.Eagerly,
                initialValue = observableLanguage.value
            )
    }

    override fun getCurrentAppLanguage(): AppLanguage = settings.appLanguage
    override suspend fun applyAppTheme(appTheme: AppTheme) {
        settings.appTheme = appTheme.also { observableTheme.emit(appTheme) }
    }

    override fun observeAppTheme(): StateFlow<AppTheme> {
        return observableTheme
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.Eagerly,
                initialValue = observableTheme.value
            )
    }

    override fun getCurrentAppTheme(): AppTheme = settings.appTheme
}