package com.devbilal.presentation.features.auth.setuppin

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

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
    data object NavigateToInitBiometric : SetupPinEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : SetupPinEffect

}
