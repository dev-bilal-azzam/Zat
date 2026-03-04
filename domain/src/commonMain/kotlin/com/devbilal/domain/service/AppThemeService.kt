package com.devbilal.domain.service

import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.domain.util.AppTheme
import kotlinx.coroutines.flow.StateFlow

class AppThemeService(
    private val settingsRepository: SettingsRepository
) {
    fun observeAppTheme(): StateFlow<AppTheme> =
        settingsRepository.observeAppTheme()
}