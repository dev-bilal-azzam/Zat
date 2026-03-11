package com.devbilal.presentation.features.initbiometric

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data object InitBiometricState : UiState

sealed interface InitBiometricIntent : UiIntent {
    data object OnEnableClicked : InitBiometricIntent
    data object OnSkipClicked : InitBiometricIntent
    data object OnBackClicked : InitBiometricIntent
}

sealed interface InitBiometricEffect : UiEffect {
    data object NavigateBack : InitBiometricEffect
    data object NavigateToHome : InitBiometricEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : InitBiometricEffect
}
