package com.devbilal.presentation.features.setuppattern

import com.devbilal.presentation.base.*

data class SetupPatternState(
    val temp: String? = null
) : UiState

sealed interface SetupPatternIntent : UiIntent {
    data object OnBackClicked : SetupPatternIntent
}

sealed interface SetupPatternEffect : UiEffect {
    data object NavigateBack : SetupPatternEffect
}
