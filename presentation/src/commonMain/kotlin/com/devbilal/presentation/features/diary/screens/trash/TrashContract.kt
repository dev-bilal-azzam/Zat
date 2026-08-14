package com.devbilal.presentation.features.diary.screens.trash

import com.devbilal.presentation.base.*

data class TrashState(
    val temp: String? = null
) : UiState

sealed interface TrashIntent : UiIntent {
    data object OnBackClicked : TrashIntent
}

sealed interface TrashEffect : UiEffect {
    data object NavigateBack : TrashEffect
}
