package com.devbilal.domain.usecase.settings

import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.domain.util.AppLanguage

class AppLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    fun getAppLanguage() = settingsRepository.getCurrentAppLanguage()

    fun observeAppLanguage() = settingsRepository.observeAppLanguage()

    suspend fun setAppLanguage(language: AppLanguage) = settingsRepository.applyAppLanguage(language)

}