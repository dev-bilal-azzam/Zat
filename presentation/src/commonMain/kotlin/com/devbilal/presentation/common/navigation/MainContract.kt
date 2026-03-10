package com.devbilal.presentation.common.navigation

import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class MainState(
    val isOnboardingDone: Boolean? = null,
    val authenticationMethod: AuthenticationMethod? = null
) : UiState {
    val isLoading: Boolean get() = isOnboardingDone == null || (isOnboardingDone && authenticationMethod == null)
}

sealed interface MainIntent : UiIntent

sealed interface MainEffect : UiEffect
