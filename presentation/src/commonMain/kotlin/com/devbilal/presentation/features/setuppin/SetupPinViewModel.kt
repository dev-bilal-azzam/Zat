package com.devbilal.presentation.features.setuppin

import com.devbilal.presentation.base.*

class SetupPinViewModel
    : BaseViewModel<SetupPinState, SetupPinIntent, SetupPinEffect>(SetupPinState()) {

    override fun handleIntent(intent: SetupPinIntent) {
        when (intent) {
            is SetupPinIntent.OnNumberClicked -> {
                if (state.value.pin.length < 4) {
                    updateState { copy(pin = pin + intent.number) }
                }
            }
            SetupPinIntent.OnBackspaceClicked -> {
                if (state.value.pin.isNotEmpty()) {
                    updateState { copy(pin = pin.dropLast(1)) }
                }
            }
            SetupPinIntent.OnConfirmClicked -> {
                if (state.value.isConfirmEnabled) {
                    sendEffect(SetupPinEffect.NavigateToHome)
                }
            }
            SetupPinIntent.OnBackClicked -> {
                sendEffect(SetupPinEffect.NavigateBack)
            }
        }
    }
}