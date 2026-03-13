package com.devbilal.presentation.features.auth.screens.setuppattern

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

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
    data object NavigateToInitBiometric : SetupPatternEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : SetupPatternEffect
}
