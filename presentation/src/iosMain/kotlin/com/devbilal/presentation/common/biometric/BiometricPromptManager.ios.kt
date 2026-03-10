package com.devbilal.presentation.common.biometric

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.scope.Scope
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAErrorBiometryNotAvailable
import platform.LocalAuthentication.LAErrorBiometryNotEnrolled
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics

class IosBiometricPromptManager : BiometricPromptManager {
    private val resultChannel = Channel<BiometricResult>()
    override val promptResults: Flow<BiometricResult>
        get() = resultChannel.receiveAsFlow()

    @OptIn(ExperimentalForeignApi::class)
    override fun showBiometricPrompt(
        title: String,
        description: String,
        cancel: String
    ) {
        val context = LAContext()

        val canEvaluate = context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, null)

        if (!canEvaluate) {
            resultChannel.trySend(BiometricResult.FeatureUnavailable)
            return
        }

        context.localizedCancelTitle = cancel

        context.evaluatePolicy(
            policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
            localizedReason = description
        ) { success, evaluationError ->
            if (success) {
                resultChannel.trySend(BiometricResult.AuthenticationSuccess)
            } else {
                when (evaluationError?.code) {
                    LAErrorBiometryNotAvailable -> resultChannel.trySend(BiometricResult.HardwareUnavailable)
                    LAErrorBiometryNotEnrolled -> resultChannel.trySend(BiometricResult.AuthenticationNotSet)
                    else -> resultChannel.trySend(BiometricResult.AuthenticationFailed)
                }
            }
        }
    }
}

actual fun Scope.createBiometricPromptManager(): BiometricPromptManager {
    return IosBiometricPromptManager()
}