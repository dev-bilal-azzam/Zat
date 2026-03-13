package com.devbilal.presentation.features.diary.screens.settings

import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class SettingsState(
    val temp: String? = null
) : UiState

sealed interface SettingsIntent : UiIntent {
    data object OnBackClicked : SettingsIntent
}

sealed interface SettingsEffect : UiEffect {
    data object NavigateBack : SettingsEffect
}
