package com.devbilal.presentation.features.home

import com.devbilal.presentation.base.BaseViewModel

class HomeViewModel(
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(HomeState()) {

    init {
    }

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}