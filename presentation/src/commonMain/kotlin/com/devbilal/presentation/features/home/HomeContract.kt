package com.devbilal.presentation.features.home

import com.devbilal.presentation.base.*

data class HomeState(
    val temp: String? = null
) : UiState

sealed interface HomeIntent : UiIntent {
    data object OnBackClicked : HomeIntent
}

sealed interface HomeEffect : UiEffect {
    data object NavigateBack : HomeEffect
}
