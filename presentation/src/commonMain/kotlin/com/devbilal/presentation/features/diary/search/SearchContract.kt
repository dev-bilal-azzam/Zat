package com.devbilal.presentation.features.diary.search

import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class SearchState(
    val temp: String? = null
) : UiState

sealed interface SearchIntent : UiIntent {
    data object OnBackClicked : SearchIntent
}

sealed interface SearchEffect : UiEffect {
    data object NavigateBack : SearchEffect
}
