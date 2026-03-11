package com.devbilal.presentation.features.auth.onboarding

import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class OnBoardingState(
    val selectedLanguage: AppLanguage = AppLanguage.English,
    val selectedTheme: AppTheme = AppTheme.DARK
): UiState

sealed interface OnBoardingIntent : UiIntent {
    data object OnContinueClicked : OnBoardingIntent
    data class OnLanguageSelected(val language: AppLanguage) : OnBoardingIntent
    data class OnThemeSelected(val theme: AppTheme) : OnBoardingIntent
}

sealed interface OnBoardingEffect : UiEffect {
    data object NavigateToInitSecurity : OnBoardingEffect
}
