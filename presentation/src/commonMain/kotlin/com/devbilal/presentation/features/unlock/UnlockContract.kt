package com.devbilal.presentation.features.unlock

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.presentation.base.*

data class UnlockState(
    val primaryMethod: PrimaryAuthenticationMethod = PrimaryAuthenticationMethod.None,
    val isBiometricEnabled: Boolean = false,
    val pin: String = "",
    val pattern: List<Int> = emptyList()
) : UiState

sealed interface UnlockIntent : UiIntent {
    data class OnNumberClicked(val number: Int) : UnlockIntent
    data object OnBackspaceClicked : UnlockIntent
    data class OnPatternChanged(val pattern: List<Int>) : UnlockIntent
    data object OnPatternCompleted : UnlockIntent
    data object OnBiometricClicked : UnlockIntent
}

sealed interface UnlockEffect : UiEffect {
    data object NavigateToHome : UnlockEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : UnlockEffect
}
