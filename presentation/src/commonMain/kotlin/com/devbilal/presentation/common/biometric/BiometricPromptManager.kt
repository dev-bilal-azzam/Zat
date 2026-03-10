package com.devbilal.presentation.common.biometric

import kotlinx.coroutines.flow.Flow
import org.koin.core.scope.Scope

interface BiometricPromptManager {
    val promptResults: Flow<BiometricResult>
    fun showBiometricPrompt(title: String, description: String, cancel: String)
}

expect fun Scope.createBiometricPromptManager(): BiometricPromptManager