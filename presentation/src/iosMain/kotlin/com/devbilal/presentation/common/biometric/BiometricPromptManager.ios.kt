package com.devbilal.presentation.common.biometric

import kotlinx.coroutines.flow.Flow
import org.koin.core.scope.Scope

class IosBiometricPromptManager : BiometricPromptManager{
    override val promptResults: Flow<BiometricResult>
        get() = TODO("Not yet implemented")

    override fun showBiometricPrompt(
        title: String,
        description: String,
        cancel: String
    ) {
        TODO("Not yet implemented")
    }
}

actual fun Scope.createBiometricPromptManager(): BiometricPromptManager {
    TODO("Not yet implemented")
}