package com.devbilal.presentation.features.settings

import com.devbilal.presentation.base.*

class SettingsViewModel(
) : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>(SettingsState()) {

    init {
    }

    override fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}