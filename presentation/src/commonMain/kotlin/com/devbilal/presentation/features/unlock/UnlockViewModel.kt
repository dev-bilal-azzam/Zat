package com.devbilal.presentation.features.unlock

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.usecase.authentication.AuthenticateWithPrimaryMethodUseCase
import com.devbilal.domain.usecase.authentication.GetAuthenticationSettingsUseCase
import com.devbilal.presentation.base.*
import com.devbilal.presentation.common.biometric.BiometricPromptManager
import com.devbilal.presentation.common.biometric.BiometricResult
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.*

class UnlockViewModel(
    private val getAuthenticationSettingsUseCase: GetAuthenticationSettingsUseCase,
    private val authenticateWithPrimaryMethodUseCase: AuthenticateWithPrimaryMethodUseCase,
    private val biometricPromptManager: BiometricPromptManager
) : BaseViewModel<UnlockState, UnlockIntent, UnlockEffect>(UnlockState()) {

    init {
        loadSettings()
        collectBiometricResult()
    }

    private fun collectBiometricResult() {
        safeCollect(
            block = { biometricPromptManager.promptResults },
            onCollect = ::onCollectBiometricResult,
            onError = { showSnackBar(messageStringResource = Res.string.biometric_failed) }
        )
    }

    private fun onCollectBiometricResult(result: BiometricResult) {
        when (result) {
            BiometricResult.AuthenticationSuccess -> { sendEffect(UnlockEffect.NavigateToHome) }
            else -> showSnackBar(messageStringResource = Res.string.biometric_failed)
        }
    }


    private fun loadSettings() {
        safeExecute(
            block = { getAuthenticationSettingsUseCase() },
            onSuccess = { settings ->
                updateState {
                    copy(
                        authenticationMethod = settings.method,
                        isBiometricEnabled = settings.isBiometricAuthEnabled
                    )
                }
            }
        )
    }

    override fun handleIntent(intent: UnlockIntent) {
        when (intent) {
            is UnlockIntent.OnNumberClicked -> onNumberClicked(intent.number)
            UnlockIntent.OnBackspaceClicked -> onBackspaceClicked()
            is UnlockIntent.OnPatternChanged -> onPatternChanged(intent.pattern)
            UnlockIntent.OnPatternCompleted -> onPatternCompleted()
            is UnlockIntent.OnBiometricClicked -> onBiometricClicked(intent.title, intent.description, intent.cancel)
        }
    }

    private fun onNumberClicked(number: Int) {
        val newPin = state.value.pin + number
        if (newPin.length <= 4) {
            updateState { copy(pin = newPin) }
            if (newPin.length == 4) {
                authenticate(AuthenticationMethod.Pin(newPin))
            }
        }
    }

    private fun onBackspaceClicked() {
        if (state.value.pin.isNotEmpty()) {
            updateState { copy(pin = state.value.pin.dropLast(1)) }
        }
    }

    private fun onPatternChanged(pattern: List<Int>) {
        updateState { copy(pattern = pattern) }
    }

    private fun onPatternCompleted() {
        if (state.value.pattern.size >= 4) {
            authenticate(AuthenticationMethod.Pattern(state.value.pattern))
        }
    }

    private fun authenticate(method: AuthenticationMethod) {
        safeExecute(
            block = { authenticateWithPrimaryMethodUseCase(method) },
            onSuccess = { sendEffect(UnlockEffect.NavigateToHome) },
            onError = {
                val message = when (method) {
                    is AuthenticationMethod.Pin -> {
                        updateState { copy(pin = "") }
                        Res.string.wrong_pin
                    }
                    is AuthenticationMethod.Pattern -> {
                        updateState { copy(pattern = emptyList()) }
                        Res.string.wrong_pattern
                    }
                    else -> Res.string.error
                }
                showSnackBar(messageStringResource = message)
            }
        )
    }

    private fun onBiometricClicked(title: String, description: String, cancel: String) {
        biometricPromptManager.showBiometricPrompt(title, description, cancel)
    }

    private fun showSnackBar(
        titleStringResource: StringResource = Res.string.error,
        messageStringResource: StringResource,
        isError: Boolean = true
    ) {
        sendEffect(
            UnlockEffect.ShowSnackBar(
                SnackBarData(
                    title = UiText.StringRes(titleStringResource),
                    message = UiText.StringRes(messageStringResource),
                    isError = isError
                )
            )
        )
    }
}
