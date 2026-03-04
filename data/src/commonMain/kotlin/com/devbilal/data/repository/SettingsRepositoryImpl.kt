package com.devbilal.data.repository

import com.devbilal.data.datasource.local.setting.appLanguage
import com.devbilal.data.datasource.local.setting.appTheme
import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.domain.util.AppLanguage
import com.devbilal.domain.util.AppTheme
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SettingsRepositoryImpl(
    private val settings: Settings,
) : SettingsRepository {
    private val observableLanguage: MutableStateFlow<String> = MutableStateFlow(settings.appLanguage)
    private val observableTheme: MutableStateFlow<String> = MutableStateFlow(settings.appTheme)

    override suspend fun applyLanguage(appLanguage: AppLanguage) {
        settings.appLanguage = appLanguage.iso.also { observableLanguage.emit(appLanguage.iso) }
    }
    override fun observeAppLanguage(): StateFlow<AppLanguage> {
      return  observableLanguage.map { it.toAppLanguage() }
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.Eagerly,
                initialValue = observableLanguage.value.toAppLanguage()
            )
    }
    override fun getCurrentAppLanguage(): AppLanguage = settings.appLanguage.toAppLanguage()
    override suspend fun applyAppTheme(appTheme: AppTheme) {
        settings.appTheme = appTheme.name.also { observableTheme.emit(appTheme.name) }
    }
    override fun observeAppTheme(): StateFlow<AppTheme> {
        return  observableTheme.map { it.toAppTheme() }
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.Eagerly,
                initialValue = observableTheme.value.toAppTheme()
            )
    }

    override fun getCurrentAppTheme(): AppTheme = settings.appTheme.toAppTheme()

    private fun String.toAppLanguage(): AppLanguage {
        return when (this) {
            AppLanguage.ENGLISH.iso -> AppLanguage.ENGLISH
            AppLanguage.ARABIC.iso -> AppLanguage.ARABIC
            else -> AppLanguage.DEFAULT
        }
    }
    private fun String.toAppTheme(): AppTheme {
        return when (this) {
            AppTheme.DARK.name -> AppTheme.DARK
            AppTheme.LIGHT.name -> AppTheme.LIGHT
            else -> AppTheme.SYSTEM
        }
    }
}