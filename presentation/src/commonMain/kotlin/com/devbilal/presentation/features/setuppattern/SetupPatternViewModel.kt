package com.devbilal.presentation.features.setuppattern

import com.devbilal.presentation.base.*

class SetupPatternViewModel(
) : BaseViewModel<SetupPatternState, SetupPatternIntent, SetupPatternEffect>(SetupPatternState()) {

    init {
    }

    override fun handleIntent(intent: SetupPatternIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}