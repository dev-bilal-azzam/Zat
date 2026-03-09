package com.devbilal.presentation.features.setuppattern

import com.devbilal.domain.usecase.authentication.SetAuthenticationSettingsUseCase
import com.devbilal.presentation.base.*

class SetupPatternViewModel(
    private val setAuthenticationSettingsUseCase: SetAuthenticationSettingsUseCase
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

                },
                onSuccess = { sendEffect(SetupPatternEffect.NavigateToHome) }
            )
        }
    }

    private fun onBackClicked() {
        sendEffect(SetupPatternEffect.NavigateBack)
    }
}
