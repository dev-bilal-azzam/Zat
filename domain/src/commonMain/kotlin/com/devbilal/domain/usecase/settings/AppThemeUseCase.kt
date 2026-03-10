package com.devbilal.domain.usecase.settings

import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.domain.util.AppTheme

class AppThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    fun getAppTheme() = settingsRepository.getCurrentAppTheme()

    fun observeAppTheme() = settingsRepository.observeAppTheme()

    suspend fun setAppTheme(theme: AppTheme) = settingsRepository.applyAppTheme(theme)

}