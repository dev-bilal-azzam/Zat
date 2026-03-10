package com.devbilal.presentation.common.navigation

import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.*

data class MainState(
    val isOnboarding: Boolean = false,
    val authenticationMethod: AuthenticationMethod? = null
) : UiState

sealed interface MainIntent : UiIntent

sealed interface MainEffect : UiEffect
