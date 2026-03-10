package com.devbilal.presentation.common.navigation

import com.devbilal.presentation.base.*

class MainViewModel(
) : BaseViewModel<MainState, MainIntent, MainEffect>(MainState()) {

    init {
        loadIsOnboarding()
    }

    private fun loadIsOnboarding() {
        TODO("Not yet implemented")
    }

    override fun handleIntent(intent: MainIntent) {}
}