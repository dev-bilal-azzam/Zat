package com.devbilal.data.repository

import com.devbilal.data.datasource.local.setting.SettingsStorage
import com.devbilal.data.datasource.local.setting.appLanguage
import com.devbilal.data.datasource.local.setting.appTheme
import com.devbilal.data.datasource.local.setting.isOnboardingDone
import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.domain.util.AppLanguage
import com.devbilal.domain.util.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SettingsRepositoryImpl(
    private val settings: SettingsStorage,
) : SettingsRepository {
    private val observableLanguage: MutableStateFlow<AppLanguage> =
        MutableStateFlow(settings.appLanguage)
    private val observableTheme: MutableStateFlow<AppTheme> = MutableStateFlow(settings.appTheme)

    override suspend fun applyAppLanguage(appLanguage: AppLanguage) {
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


    override suspend fun isOnboardingDone(): Boolean = settings.isOnboardingDone

    override suspend fun setIsOnboardingDone() {
        settings.isOnboardingDone = true
    }
}