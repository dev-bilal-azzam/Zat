package com.devbilal.presentation.features.onboarding

import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.domain.repository.SettingsRepository
import com.devbilal.presentation.base.BaseViewModel
import com.devbilal.domain.util.AppLanguage as SettingsAppLanguage

class OnBoardingViewModel(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<OnBoardingState, OnBoardingIntent, OnBoardingEffect>(OnBoardingState()) {

    init {
        getLanguage()
        getTheme()
    }

    override fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {
            OnBoardingIntent.OnContinueClicked -> onContinueClicked()
            is OnBoardingIntent.OnLanguageSelected -> selectLanguage(intent.language)
            is OnBoardingIntent.OnThemeSelected -> selectTheme(intent.theme)
        }
    }


    private fun getTheme() {
        safeExecute(
            block = settingsRepository::getCurrentAppTheme,
            onSuccess = { updateState { copy(selectedTheme = it.toAppTheme()) } }
        )
    }

    private fun getLanguage() {
        safeExecute(
            block = settingsRepository::getCurrentAppLanguage,
            onSuccess = { updateState { copy(selectedLanguage = AppLanguage.fromIso(it.iso)) } }
        )
    }

    private fun selectLanguage(language: AppLanguage) {
        safeExecute(
            block = {
                settingsRepository.applyLanguage(
                    SettingsAppLanguage.fromIso(language.iso)
                )
            },
            onSuccess = { updateState { copy(selectedLanguage = language) } }
        )
    }

    private fun selectTheme(theme: AppTheme) {
        safeExecute(
            block = {
                settingsRepository.applyAppTheme(theme.toSettingsAppTheme())
            },
            onSuccess = { updateState { copy(selectedTheme = theme) } }
        )
    }

    private fun onContinueClicked() { sendEffect(OnBoardingEffect.NavigateToInitSecurity) }

}