package com.devbilal.presentation.features.setuppin

import com.devbilal.presentation.base.*

class SetupPinViewModel(
) : BaseViewModel<SetupPinState, SetupPinIntent, SetupPinEffect>(SetupPinState()) {

    init {
    }

    override fun handleIntent(intent: SetupPinIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}