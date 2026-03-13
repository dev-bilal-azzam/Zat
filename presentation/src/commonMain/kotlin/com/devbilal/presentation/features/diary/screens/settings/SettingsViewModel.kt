package com.devbilal.presentation.features.diary.screens.settings

import com.devbilal.presentation.base.BaseViewModel

class SettingsViewModel : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>(
    SettingsState()
) {

    init {
    }

    override fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}