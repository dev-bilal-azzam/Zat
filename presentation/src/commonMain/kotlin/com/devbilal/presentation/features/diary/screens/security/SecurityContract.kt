package com.devbilal.presentation.features.diary.screens.security

import com.devbilal.presentation.base.*

data class SecurityState(
    val temp: String? = null
) : UiState

sealed interface SecurityIntent : UiIntent {
    data object OnBackClicked : SecurityIntent
}

sealed interface SecurityEffect : UiEffect {
    data object NavigateBack : SecurityEffect
}
