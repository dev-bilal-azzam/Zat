package com.devbilal.presentation.features.initbiometric

import com.devbilal.domain.usecase.authentication.BiometricAuthenticationUseCase
import com.devbilal.presentation.base.BaseViewModel

class InitBiometricViewModel (
    private val biometricAuthenticationUseCase: BiometricAuthenticationUseCase
): BaseViewModel<InitBiometricState, InitBiometricIntent, InitBiometricEffect>(InitBiometricState) {

    override fun handleIntent(intent: InitBiometricIntent) {
        when (intent) {
            InitBiometricIntent.OnEnableClicked -> onEnableClicked()
            InitBiometricIntent.OnSkipClicked -> onSkipClicked()
            InitBiometricIntent.OnBackClicked -> onBackClicked()
        }
    }

    private fun onEnableClicked() {
        safeExecute(
            block = { biometricAuthenticationUseCase.enable() },
            onSuccess = { sendEffect(InitBiometricEffect.NavigateToHome) }
        )

    }

    private fun onSkipClicked() {
        safeExecute(
            block = { biometricAuthenticationUseCase.enable() },
            onSuccess = { sendEffect(InitBiometricEffect.NavigateToHome) }
        )
    }

    private fun onBackClicked() {
        sendEffect(InitBiometricEffect.NavigateBack)
    }
}
