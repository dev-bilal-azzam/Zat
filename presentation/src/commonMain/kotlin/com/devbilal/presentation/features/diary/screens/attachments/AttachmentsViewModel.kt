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
            is AttachmentsIntent.OnPageChanged -> updateState { copy(currentIndex = intent.index) }
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
