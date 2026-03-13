package com.devbilal.presentation.features.auth.screens.initsecurity

import com.devbilal.presentation.base.BaseViewModel

class InitSecurityViewModel :
    BaseViewModel<InitSecurityState, InitSecurityIntent, InitSecurityEffect>(
        InitSecurityState
    ) {

    override fun handleIntent(intent: InitSecurityIntent) {
        when (intent) {
            InitSecurityIntent.OnBackClicked -> sendEffect(
                InitSecurityEffect.NavigateBack)
            InitSecurityIntent.OnPatternClicked -> sendEffect(
                InitSecurityEffect.NavigateToSetPattern)
            InitSecurityIntent.OnPinClicked -> sendEffect(
                InitSecurityEffect.NavigateToSetPin)
            InitSecurityIntent.OnSetupLaterClicked -> sendEffect(
                InitSecurityEffect.NavigateToHome)
        }
    }
}