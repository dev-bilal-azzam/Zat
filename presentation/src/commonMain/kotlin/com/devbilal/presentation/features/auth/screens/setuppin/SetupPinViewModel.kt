package com.devbilal.presentation.features.auth.screens.setuppin

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.usecase.authentication.SetAuthenticationMethodUseCase
import com.devbilal.presentation.base.BaseViewModel
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.couldnt_set_pinn
import zat.presentation.generated.resources.error

class SetupPinViewModel(
    private val setAuthenticationMethodUseCase: SetAuthenticationMethodUseCase
) : BaseViewModel<SetupPinState, SetupPinIntent, SetupPinEffect>(
    SetupPinState()
) {

    override fun handleIntent(intent: SetupPinIntent) {
        when (intent) {
            is SetupPinIntent.OnNumberClicked -> onNumberClicked(intent.number)
            SetupPinIntent.OnBackspaceClicked -> onBackspaceClicked()
            SetupPinIntent.OnConfirmClicked -> onConfirmClicked()
            SetupPinIntent.OnBackClicked -> onBackClicked()
        }
    }

    private fun onNumberClicked(number: Int) {
        if (state.value.pin.length < 4) {
            updateState { copy(pin = pin + number) }
        }
    }

    private fun onBackspaceClicked() {
        if (state.value.pin.isNotEmpty()) {
            updateState { copy(pin = pin.dropLast(1)) }
        }
    }

    private fun onConfirmClicked() {
        if (state.value.isConfirmEnabled) {
            safeExecute(
                block = {
                    setAuthenticationMethodUseCase(
                        method = AuthenticationMethod.Pin(currentState.pin)
                    )
                },
                onSuccess = { sendEffect(SetupPinEffect.SuccessfulSetup) },
                onError = { showSnackBar() }
            )
        }
    }

    private fun onBackClicked() {
        sendEffect(SetupPinEffect.NavigateBack)
    }

    private fun showSnackBar(
        titleStringResource: StringResource = Res.string.error,
        messageStringResource: StringResource = Res.string.couldnt_set_pinn,
        isError: Boolean = false
    ) {
        sendEffect(
            SetupPinEffect.ShowSnackBar(
                SnackBarData(
                    title = UiText.StringRes(titleStringResource),
                    message = UiText.StringRes(messageStringResource),
                    isError = isError
                )
            )
        )
    }
}