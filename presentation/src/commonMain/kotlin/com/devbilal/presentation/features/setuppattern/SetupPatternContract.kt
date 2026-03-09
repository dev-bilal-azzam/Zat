package com.devbilal.presentation.features.setuppattern

import com.devbilal.presentation.base.*

data class SetupPatternState(
    val pattern: List<Int> = emptyList(),
) : UiState {
    val isConfirmEnabled: Boolean get() = pattern.size >= 4
}

sealed interface SetupPatternIntent : UiIntent {
    data class OnPatternChanged(val pattern: List<Int>) : SetupPatternIntent
    data object OnConfirmClicked : SetupPatternIntent
    data object OnBackClicked : SetupPatternIntent
}

sealed interface SetupPatternEffect : UiEffect {
    data object NavigateBack : SetupPatternEffect
    data object NavigateToHome : SetupPatternEffect
}
