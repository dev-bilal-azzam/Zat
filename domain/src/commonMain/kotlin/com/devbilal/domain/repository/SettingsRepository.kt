package com.devbilal.domain.repository

import com.devbilal.domain.util.AppLanguage
import com.devbilal.domain.util.AppTheme
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    suspend fun applyAppLanguage(appLanguage: AppLanguage)
    fun observeAppLanguage(): StateFlow<AppLanguage>
    fun getCurrentAppLanguage(): AppLanguage
    suspend fun applyAppTheme(appTheme: AppTheme)
    fun observeAppTheme(): StateFlow<AppTheme>
    fun getCurrentAppTheme(): AppTheme

    suspend fun isOnboardingDone(): Boolean

    suspend fun setIsOnboardingDone()
}