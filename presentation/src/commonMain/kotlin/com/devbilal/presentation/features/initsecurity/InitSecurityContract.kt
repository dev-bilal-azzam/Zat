package com.devbilal.presentation.features.initsecurity

import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data object InitSecurityState : UiState

sealed interface InitSecurityIntent : UiIntent {
    data object OnBackClicked : InitSecurityIntent
    data object OnPinClicked : InitSecurityIntent
    data object OnPatternClicked : InitSecurityIntent
    data object OnSetupLaterClicked : InitSecurityIntent
}

sealed interface InitSecurityEffect : UiEffect {
    data object NavigateBack : InitSecurityEffect
    data object NavigateToHome: InitSecurityEffect
    data object NavigateToSetPin: InitSecurityEffect
    data object NavigateToSetPattern: InitSecurityEffect
}
