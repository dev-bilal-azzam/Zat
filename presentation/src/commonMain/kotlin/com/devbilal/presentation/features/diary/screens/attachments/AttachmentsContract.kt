package com.devbilal.presentation.features.diary.screens.attachments

import com.devbilal.domain.entity.Attachment
import com.devbilal.presentation.base.*

data class AudioPlaybackState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val seekToPosition: Long? = null
)

data class AttachmentsState(
    val attachments: List<Attachment> = emptyList(),
    val currentIndex: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val audioPlaybackState: AudioPlaybackState = AudioPlaybackState()
) : UiState

sealed interface AttachmentsIntent : UiIntent {
    data object OnBackClicked : AttachmentsIntent
    data class OnPageChanged(val index: Int) : AttachmentsIntent
    data object ToggleAudioPlayback : AttachmentsIntent
    data object StopAudioPlayback : AttachmentsIntent
    data object OnAudioPlaybackCompleted : AttachmentsIntent
    data class SeekAudioTo(val positionMs: Long) : AttachmentsIntent
    data class UpdateAudioProgress(val currentMs: Long, val totalMs: Long) : AttachmentsIntent
}

sealed interface AttachmentsEffect : UiEffect {
    data object NavigateBack : AttachmentsEffect
}
