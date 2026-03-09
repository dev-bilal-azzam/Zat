package com.devbilal.presentation.features.setuppattern

import com.devbilal.presentation.base.*

class SetupPatternViewModel(
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
            sendEffect(SetupPatternEffect.NavigateToHome)
        }
    }

    private fun onBackClicked() {
        sendEffect(SetupPatternEffect.NavigateBack)
    }
}
