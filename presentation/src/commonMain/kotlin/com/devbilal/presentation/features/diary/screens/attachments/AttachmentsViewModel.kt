@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.attachments

import com.devbilal.domain.usecase.diary.GetDiaryEntryUseCase
import com.devbilal.presentation.base.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AttachmentsViewModel(
    private val args: AttachmentsArgs,
    private val getDiaryEntryUseCase: GetDiaryEntryUseCase
) : BaseViewModel<AttachmentsState, AttachmentsIntent, AttachmentsEffect>(
    AttachmentsState(currentIndex = args.initialIndex)
) {

    init {
        loadAttachments()
    }

    override fun handleIntent(intent: AttachmentsIntent) {
        when (intent) {
            AttachmentsIntent.OnBackClicked -> sendEffect(AttachmentsEffect.NavigateBack)
            is AttachmentsIntent.OnPageChanged -> updateState { 
                copy(
                    currentIndex = intent.index,
                    audioPlaybackState = AudioPlaybackState() // Reset audio on page change
                ) 
            }
            AttachmentsIntent.ToggleAudioPlayback -> updateState {
                copy(audioPlaybackState = audioPlaybackState.copy(isPlaying = !audioPlaybackState.isPlaying, seekToPosition = null))
            }
            AttachmentsIntent.StopAudioPlayback -> updateState {
                copy(audioPlaybackState = audioPlaybackState.copy(isPlaying = false, currentPosition = 0, seekToPosition = 0L))
            }
            is AttachmentsIntent.SeekAudioTo -> updateState {
                copy(audioPlaybackState = audioPlaybackState.copy(seekToPosition = intent.positionMs))
            }
            is AttachmentsIntent.UpdateAudioProgress -> updateState {
                copy(audioPlaybackState = audioPlaybackState.copy(
                    currentPosition = intent.currentMs,
                    totalDuration = intent.totalMs,
                    seekToPosition = null // Clear seek request once updated
                ))
            }
        }
    }

    private fun loadAttachments() {
        safeExecute(
            onStart = { updateState { copy(isLoading = true) } },
            onSuccess = { entry: com.devbilal.domain.entity.DiaryEntry ->
                updateState { copy(attachments = entry.attachments, isLoading = false) }
            },
            onError = { e ->
                updateState { copy(error = e.message, isLoading = false) }
            },
            block = { getDiaryEntryUseCase(Uuid.parse(args.entryId)) }
        )
    }
}
