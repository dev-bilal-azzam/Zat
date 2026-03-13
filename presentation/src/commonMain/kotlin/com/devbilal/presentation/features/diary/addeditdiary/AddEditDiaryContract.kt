package com.devbilal.presentation.features.diary.addeditdiary

import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class AddEditDiaryState(
    val temp: String? = null
) : UiState

sealed interface AddEditDiaryIntent : UiIntent {
    data object OnBackClicked :
        AddEditDiaryIntent
}

sealed interface AddEditDiaryEffect : UiEffect {
    data object NavigateBack :
        AddEditDiaryEffect
}
