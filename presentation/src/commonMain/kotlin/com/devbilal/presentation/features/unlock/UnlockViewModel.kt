package com.devbilal.presentation.features.unlock

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.domain.usecase.authentication.AuthenticateWithPrimaryMethodUseCase
import com.devbilal.domain.usecase.authentication.GetAuthenticationSettingsUseCase
import com.devbilal.presentation.base.*
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.*

class UnlockViewModel(
    private val getAuthenticationSettingsUseCase: GetAuthenticationSettingsUseCase,
    private val authenticateWithPrimaryMethodUseCase: AuthenticateWithPrimaryMethodUseCase
) : BaseViewModel<UnlockState, UnlockIntent, UnlockEffect>(UnlockState()) {

    init {
        loadSettings()
    }

    private fun loadSettings() {
        safeExecute(
            block = { getAuthenticationSettingsUseCase() },
            onSuccess = { settings ->
                updateState {
                    copy(
                        primaryMethod = settings.primaryMethod,
                        isBiometricEnabled = settings.biometricMethods.isNotEmpty()
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
            UnlockIntent.OnBiometricClicked -> onBiometricClicked()
        }
    }

    private fun onNumberClicked(number: Int) {
        val newPin = state.value.pin + number
        if (newPin.length <= 4) {
            updateState { copy(pin = newPin) }
            if (newPin.length == 4) {
                authenticate(PrimaryAuthenticationMethod.Pin(newPin))
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
        if (pattern.size >= 4) {
            authenticate(PrimaryAuthenticationMethod.Pattern(pattern))
        }
    }

    private fun authenticate(method: PrimaryAuthenticationMethod) {
        safeExecute(
            block = { authenticateWithPrimaryMethodUseCase(method) },
            onSuccess = { sendEffect(UnlockEffect.NavigateToHome) },
            onError = {
                val message = when (method) {
                    is PrimaryAuthenticationMethod.Pin -> {
                        updateState { copy(pin = "") }
                        Res.string.wrong_pin
                    }
                    is PrimaryAuthenticationMethod.Pattern -> {
                        updateState { copy(pattern = emptyList()) }
                        Res.string.wrong_pattern
                    }
                    else -> Res.string.error
                }
                showSnackBar(messageStringResource = message)
            }
        )
    }

    private fun onBiometricClicked() {
        // Biometric logic would go here
        sendEffect(UnlockEffect.NavigateToHome)
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
