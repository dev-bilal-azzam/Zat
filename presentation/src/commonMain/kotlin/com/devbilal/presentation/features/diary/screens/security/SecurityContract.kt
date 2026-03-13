package com.devbilal.presentation.features.diary.screens.security

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class SecurityState(
    val isProtectionEnabled: Boolean = false,
    val selectedMethod: AuthenticationMethod? = null,
    val isBiometricEnabled: Boolean = false
) : UiState

sealed interface SecurityIntent : UiIntent {
    data object OnBackClicked : SecurityIntent
    data class OnToggleProtection(val enabled: Boolean) : SecurityIntent
    data class OnMethodSelected(val method: AuthenticationMethod) : SecurityIntent
    data class OnToggleBiometric(val enabled: Boolean) : SecurityIntent
    data object OnChangePinClicked : SecurityIntent
    data object OnChangePatternClicked : SecurityIntent
    data object OnSuccessfulSetup : SecurityIntent
}

sealed interface SecurityEffect : UiEffect {
    data object NavigateBack : SecurityEffect
    data object NavigateToUnlockForChangePin : SecurityEffect
    data object NavigateToUnlockForChangePattern : SecurityEffect
    data object NavigateToSetupPin : SecurityEffect
    data object NavigateToSetupPattern : SecurityEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : SecurityEffect
}
