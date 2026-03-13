package com.devbilal.presentation.features.diary.screens.security

import com.devbilal.presentation.base.*

class SecurityViewModel(
) : BaseViewModel<SecurityState, SecurityIntent, SecurityEffect>(SecurityState()) {

    init {
    }

    override fun handleIntent(intent: SecurityIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}