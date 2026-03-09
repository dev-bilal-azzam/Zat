package com.devbilal.presentation.features.initbiometric

import com.devbilal.presentation.base.BaseViewModel

class InitBiometricViewModel
    : BaseViewModel<InitBiometricState, InitBiometricIntent, InitBiometricEffect>(InitBiometricState) {

    override fun handleIntent(intent: InitBiometricIntent) {
        when (intent) {
            InitBiometricIntent.OnEnableClicked -> onEnableClicked()
            InitBiometricIntent.OnSkipClicked -> onSkipClicked()
            InitBiometricIntent.OnBackClicked -> onBackClicked()
        }
    }

    private fun onEnableClicked() {
        TODO("Set System's Biometric Authentication")
    }

    private fun onSkipClicked() {
        sendEffect(InitBiometricEffect.NavigateToHome)
    }

    private fun onBackClicked() {
        sendEffect(InitBiometricEffect.NavigateBack)
    }
}
