package com.devbilal.presentation.features.setuppin

import com.devbilal.presentation.base.*

data class SetupPinState(
    val pin: String = ""
) : UiState {
    val isConfirmEnabled: Boolean get() = pin.length == 4
}

sealed interface SetupPinIntent : UiIntent {
    data class OnNumberClicked(val number: Int) : SetupPinIntent
    data object OnBackspaceClicked : SetupPinIntent
    data object OnConfirmClicked : SetupPinIntent
    data object OnBackClicked : SetupPinIntent
}

sealed interface SetupPinEffect : UiEffect {
    data object NavigateBack : SetupPinEffect
    data object NavigateToHome : SetupPinEffect
}
