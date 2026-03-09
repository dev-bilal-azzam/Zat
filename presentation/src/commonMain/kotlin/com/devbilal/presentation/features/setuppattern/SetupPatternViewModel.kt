package com.devbilal.presentation.features.setuppattern

import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.domain.usecase.authentication.SetPrimaryAuthenticationMethodUseCase
import com.devbilal.presentation.base.*

class SetupPatternViewModel(
    private val setPrimaryAuthenticationMethodUseCase: SetPrimaryAuthenticationMethodUseCase
) : BaseViewModel<SetupPatternState, SetupPatternIntent, SetupPatternEffect>(SetupPatternState()) {

    override fun handleIntent(intent: SetupPatternIntent) {
        when (intent) {
            is SetupPatternIntent.OnPatternChanged -> onPatternChanged(intent.pattern)
            SetupPatternIntent.OnConfirmClicked -> onConfirmClicked()
            SetupPatternIntent.OnBackClicked -> onBackClicked()
        }
    }

    private fun onPatternChanged(pattern: List<Int>) {
        updateState { copy(pattern = pattern) }
    }

    private fun onConfirmClicked() {
        if (state.value.isConfirmEnabled) {
            safeExecute(
                block = {
                    setPrimaryAuthenticationMethodUseCase(
                        method = PrimaryAuthenticationMethod.Pattern(currentState.pattern)
                    )
                },
                onSuccess = { sendEffect(SetupPatternEffect.NavigateToHome) },
                onError = { /*sendEffect(SetupPatternEffect.ShowError())*/ }
            )
        }
    }

    private fun onBackClicked() {
        sendEffect(SetupPatternEffect.NavigateBack)
    }
}
