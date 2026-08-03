package com.devbilal.presentation.features.diary.screens.attachments

import com.devbilal.presentation.base.*

data class AttachmentsState(
    val temp: String? = null
) : UiState

sealed interface AttachmentsIntent : UiIntent {
    data object OnBackClicked : AttachmentsIntent
}

sealed interface AttachmentsEffect : UiEffect {
    data object NavigateBack : AttachmentsEffect
}
