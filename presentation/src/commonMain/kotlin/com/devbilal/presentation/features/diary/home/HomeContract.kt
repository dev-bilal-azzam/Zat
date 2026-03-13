package com.devbilal.presentation.features.diary.home

import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class HomeState(
    val temp: String? = null
) : UiState

sealed interface HomeIntent : UiIntent {
    data object OnBackClicked : HomeIntent
}

sealed interface HomeEffect : UiEffect {
    data object NavigateBack : HomeEffect
}
