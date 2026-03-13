package com.devbilal.presentation.features.diary.screens.settings

import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.domain.usecase.settings.AppLanguageUseCase
import com.devbilal.domain.usecase.settings.AppThemeUseCase
import com.devbilal.presentation.base.BaseViewModel
import com.devbilal.presentation.features.auth.screens.onboarding.toAppTheme
import com.devbilal.presentation.features.auth.screens.onboarding.toSettingsAppTheme
import com.devbilal.domain.util.AppLanguage as SettingsAppLanguage

class SettingsViewModel(
    private val appLanguageUseCase: AppLanguageUseCase,
    private val appThemeUseCase: AppThemeUseCase,
) : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>(
    SettingsState()
) {

    init {
        getLanguage()
        getTheme()
    }

    override fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.OnBackClicked -> sendEffect(SettingsEffect.NavigateBack)
            SettingsIntent.OnSecurityClicked -> sendEffect(SettingsEffect.NavigateToSecuritySettings)
            is SettingsIntent.OnThemeSelected -> selectTheme(intent.theme)
            is SettingsIntent.OnLanguageSelected -> selectLanguage(intent.language)
            SettingsIntent.OnExportClicked -> { /* No logic needed */
            }

            SettingsIntent.OnImportClicked -> { /* No logic needed */
            }

            SettingsIntent.OnPrivacyPolicyClicked -> { /* No logic needed */
            }

            SettingsIntent.OnContactSupportClicked -> { /* No logic needed */
            }
        }
    }

    private fun getTheme() {
        safeExecute(
            block = appThemeUseCase::getAppTheme,
            onSuccess = { updateState { copy(selectedTheme = it.toAppTheme()) } }
        )
    }

    private fun getLanguage() {
        safeExecute(
            block = appLanguageUseCase::getAppLanguage,
            onSuccess = { updateState { copy(selectedLanguage = AppLanguage.fromIso(it.iso)) } }
        )
    }

    private fun selectLanguage(language: AppLanguage) {
        safeExecute(
            block = {
                appLanguageUseCase.setAppLanguage(
                    SettingsAppLanguage.fromIso(language.iso)
                )
            },
            onSuccess = { updateState { copy(selectedLanguage = language) } }
        )
    }

    private fun selectTheme(theme: AppTheme) {
        safeExecute(
            block = {
                appThemeUseCase.setAppTheme(theme.toSettingsAppTheme())
            },
            onSuccess = { updateState { copy(selectedTheme = theme) } }
        )
    }
}
