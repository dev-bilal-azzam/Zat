package com.devbilal.presentation.features.settings

import com.devbilal.presentation.base.*

data class SettingsState(
    val temp: String? = null
) : UiState

sealed interface SettingsIntent : UiIntent {
    data object OnBackClicked : SettingsIntent
}

sealed interface SettingsEffect : UiEffect {
    data object NavigateBack : SettingsEffect
}
