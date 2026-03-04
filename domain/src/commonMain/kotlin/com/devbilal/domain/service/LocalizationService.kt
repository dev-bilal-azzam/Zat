package com.devbilal.domain.service

import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.domain.util.AppLanguage
import kotlinx.coroutines.flow.StateFlow

class LocalizationService(
    private val settingsRepository: SettingsRepository
) {
    fun observeLanguage(): StateFlow<AppLanguage> =
        settingsRepository.observeAppLanguage()

    fun getCurrentLanguage(): AppLanguage =
        settingsRepository.getCurrentAppLanguage()
}