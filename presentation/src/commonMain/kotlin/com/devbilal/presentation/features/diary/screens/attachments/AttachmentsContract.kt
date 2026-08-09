package com.devbilal.presentation.features.diary.screens.attachments

import com.devbilal.domain.entity.Attachment
import com.devbilal.presentation.base.*

data class AttachmentsState(
    val attachments: List<Attachment> = emptyList(),
    val currentIndex: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState

sealed interface AttachmentsIntent : UiIntent {
    data object OnBackClicked : AttachmentsIntent
    data class OnPageChanged(val index: Int) : AttachmentsIntent
}

sealed interface AttachmentsEffect : UiEffect {
    data object NavigateBack : AttachmentsEffect
}
