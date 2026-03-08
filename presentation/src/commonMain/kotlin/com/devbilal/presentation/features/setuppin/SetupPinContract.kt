package com.devbilal.presentation.features.setuppin

import com.devbilal.presentation.base.*

data class SetupPinState(
    val temp: String? = null
) : UiState

sealed interface SetupPinIntent : UiIntent {
    data object OnBackClicked : SetupPinIntent
}

sealed interface SetupPinEffect : UiEffect {
    data object NavigateBack : SetupPinEffect
}
