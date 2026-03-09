package com.devbilal.presentation.features.setuppattern

import com.devbilal.presentation.base.*

class SetupPatternViewModel(
) : BaseViewModel<SetupPatternState, SetupPatternIntent, SetupPatternEffect>(SetupPatternState()) {

    override fun handleIntent(intent: SetupPatternIntent) {
        when (intent) {
            is SetupPatternIntent.OnPatternChanged -> {
                updateState { copy(pattern = intent.pattern) }
            }
            SetupPatternIntent.OnConfirmClicked -> {
                if (state.value.isConfirmEnabled) {
                    sendEffect(SetupPatternEffect.NavigateToHome)
                }
            }
            SetupPatternIntent.OnBackClicked -> {
                sendEffect(SetupPatternEffect.NavigateBack)
            }
        }
    }
}