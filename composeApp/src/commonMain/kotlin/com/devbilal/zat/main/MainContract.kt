package com.devbilal.zat.main

import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class MainState(
    val isOnboardingDone: Boolean? = null,
    val authenticationMethod: AuthenticationMethod? = null,
    val currentDestination: ZatDestination = ZatDestination.Loading
) : UiState

enum class ZatDestination {
    Home,
    Auth,
    Loading
}

sealed interface MainIntent : UiIntent {
    data object NavigateHome: MainIntent
}

sealed interface MainEffect : UiEffect {
    data object NavigateHome: MainEffect
}
