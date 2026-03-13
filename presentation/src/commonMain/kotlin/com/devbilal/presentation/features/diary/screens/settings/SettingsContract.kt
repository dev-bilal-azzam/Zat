package com.devbilal.presentation.features.diary.screens.settings

import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class SettingsState(
    val selectedLanguage: AppLanguage = AppLanguage.English,
    val selectedTheme: AppTheme = AppTheme.DARK
) : UiState

sealed interface SettingsIntent : UiIntent {
    data object OnBackClicked : SettingsIntent
    data object OnSecurityClicked : SettingsIntent
    data class OnThemeSelected(val theme: AppTheme) : SettingsIntent
    data class OnLanguageSelected(val language: AppLanguage) : SettingsIntent
    data object OnExportClicked : SettingsIntent
    data object OnImportClicked : SettingsIntent
    data object OnPrivacyPolicyClicked : SettingsIntent
    data object OnContactSupportClicked : SettingsIntent
}

sealed interface SettingsEffect : UiEffect {
    data object NavigateBack : SettingsEffect
    data object NavigateToSecuritySettings : SettingsEffect
}
