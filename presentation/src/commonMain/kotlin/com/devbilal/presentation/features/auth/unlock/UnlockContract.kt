package com.devbilal.presentation.features.auth.unlock

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class UnlockState(
    val authenticationMethod: AuthenticationMethod = AuthenticationMethod.None,
    val isBiometricEnabled: Boolean = false,
    val pin: String = "",
    val pattern: List<Int> = emptyList()
) : UiState

sealed interface UnlockIntent : UiIntent {
    data class OnNumberClicked(val number: Int) : UnlockIntent
    data object OnBackspaceClicked : UnlockIntent
    data class OnPatternChanged(val pattern: List<Int>) : UnlockIntent
    data object OnPatternCompleted : UnlockIntent
    data class OnBiometricClicked(
        val title: String,
        val description: String,
        val cancel:String
    ) : UnlockIntent
}

sealed interface UnlockEffect : UiEffect {
    data object NavigateToHome : UnlockEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : UnlockEffect
}
