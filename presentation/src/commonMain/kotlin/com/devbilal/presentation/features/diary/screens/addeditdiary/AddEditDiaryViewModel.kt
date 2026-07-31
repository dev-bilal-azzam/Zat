@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.addeditdiary

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.entity.DiaryEntry
import com.devbilal.domain.usecase.diary.EditDiaryEntryUseCase
import com.devbilal.domain.usecase.diary.GetDiaryEntryUseCase
import com.devbilal.domain.usecase.diary.SaveDiaryEntryUseCase
import com.devbilal.domain.util.now
import com.devbilal.presentation.base.BaseViewModel
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.diary_entry_not_saved
import zat.presentation.generated.resources.diary_entry_saved
import zat.presentation.generated.resources.diary_not_found
import zat.presentation.generated.resources.diary_title_cannot_be_empty
import zat.presentation.generated.resources.error
import zat.presentation.generated.resources.success
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AddEditDiaryViewModel(
    args: AddEditDiaryArgs,
    private val getDiaryEntryUseCase: GetDiaryEntryUseCase,
    private val editDiaryEntryUseCase: EditDiaryEntryUseCase,
    private val saveDiaryEntryUseCase: SaveDiaryEntryUseCase
) : BaseViewModel<AddEditDiaryState, AddEditDiaryIntent, AddEditDiaryEffect>(
    AddEditDiaryState(date = LocalDateTime.now().date)
) {

    init {
        args.entryId?.let { loadEntry(it) }
    }

    override fun handleIntent(intent: AddEditDiaryIntent) {
        when (intent) {
            AddEditDiaryIntent.OnBackClicked -> sendEffect(AddEditDiaryEffect.NavigateBack)
            AddEditDiaryIntent.OnSaveClicked -> saveEntry()
            is AddEditDiaryIntent.OnTitleChanged -> updateState { copy(title = intent.title) }
            is AddEditDiaryIntent.OnContentChanged -> updateState { copy(content = intent.content) }
            is AddEditDiaryIntent.OnDateChanged -> updateState { copy(date = intent.date) }
            is AddEditDiaryIntent.OnColorChanged -> updateState { copy(color = intent.color) }
            AddEditDiaryIntent.OnPickDateClicked -> updateState { copy(isDatePickerVisible = true) }
            AddEditDiaryIntent.OnDismissDatePicker -> updateState { copy(isDatePickerVisible = false) }
            AddEditDiaryIntent.OnAttachAudioClicked -> updateState { copy(isAttachAudioOverlayVisible = true) }
            AddEditDiaryIntent.OnAttachImageClicked -> updateState { copy(isAttachImageOverlayVisible = true) }
            AddEditDiaryIntent.OnAttachVideoClicked -> updateState { copy(isAttachVideoOverlayVisible = true) }
            AddEditDiaryIntent.OnDismissAttachAudioOverlay -> updateState { copy(isAttachAudioOverlayVisible = false) }
            AddEditDiaryIntent.OnDismissAttachImageOverlay -> updateState { copy(isAttachImageOverlayVisible = false) }
            AddEditDiaryIntent.OnDismissAttachVideoOverlay -> updateState { copy(isAttachVideoOverlayVisible = false) }
            AddEditDiaryIntent.OnCapturePhotoClicked -> sendEffect(AddEditDiaryEffect.LaunchCamera)
            AddEditDiaryIntent.OnRecordVideoClicked -> sendEffect(AddEditDiaryEffect.LaunchVideoRecorder)
            AddEditDiaryIntent.OnRecordAudioClicked -> {
                updateState { copy(isAttachAudioOverlayVisible = false) }
                sendEffect(AddEditDiaryEffect.LaunchAudioRecorder)
            }
            AddEditDiaryIntent.OnStopRecordAudioClicked -> sendEffect(AddEditDiaryEffect.StopAudioRecorder)
            is AddEditDiaryIntent.OnAddAttachment -> updateState {
                copy(
                    attachments = attachments + intent.attachment,
                    isAttachAudioOverlayVisible = false,
                    isAttachImageOverlayVisible = false,
                    isAttachVideoOverlayVisible = false,
                    isRecordingAudio = false
                )
            }
            is AddEditDiaryIntent.OnRemoveAttachment -> updateState {
                copy(attachments = attachments - intent.attachment)
            }
            AddEditDiaryIntent.OnStartRecordAudio -> updateState { copy(isRecordingAudio = true) }
            AddEditDiaryIntent.OnPickAudioClicked -> sendEffect(AddEditDiaryEffect.LaunchAudioPicker)
            AddEditDiaryIntent.OnPickImageClicked -> sendEffect(AddEditDiaryEffect.LaunchImagePicker)
            AddEditDiaryIntent.OnPickVideoClicked -> sendEffect(AddEditDiaryEffect.LaunchVideoPicker)
        }
    }

    private fun loadEntry(id: String) {
        safeExecute(
            onStart = { updateState { copy(isLoading = true) } },
            block = { getDiaryEntryUseCase(Uuid.parse(id)) },
            onSuccess = ::onLoadEntrySuccess,
            onError = { showSnackBar(messageStringResource = Res.string.diary_not_found) }
        )
    }

    private fun onLoadEntrySuccess(entry: DiaryEntry) {
        updateState {
            copy(
                id = entry.id.toString(),
                title = entry.title,
                content = entry.content,
                date = entry.date,
                color = entry.color,
                attachments = entry.attachments,
                isEditMode = true,
                isLoading = false
            )
        }
    }

    private fun saveEntry() {

        if (currentState.title.isBlank()) {
            showSnackBar(messageStringResource = Res.string.diary_title_cannot_be_empty)
            return
        }

        safeExecute(
            onSuccess = { onSaveEntrySuccess() },
            onError = {
                showSnackBar(messageStringResource = Res.string.diary_entry_not_saved)
            }
        ) {
            val entry = DiaryEntry(
                id = currentState.id?.let { Uuid.parse(it) } ?: Uuid.random(),
                title = currentState.title,
                content = currentState.content,
                date = currentState.date,
                createdAt = LocalDateTime.now(),
                color = currentState.color,
                attachments = currentState.attachments
            )

            if (currentState.isEditMode) {
                editDiaryEntryUseCase(entry)
            } else {
                saveDiaryEntryUseCase(entry)
            }
        }
    }

    private fun onSaveEntrySuccess() {
        showSnackBar(
            titleStringResource = Res.string.success,
            messageStringResource = Res.string.diary_entry_saved,
            isError = false
        )
        sendEffect(AddEditDiaryEffect.NavigateBack)
    }

    private fun showSnackBar(
        titleStringResource: StringResource = Res.string.error,
        messageStringResource: StringResource,
        isError: Boolean = true
    ) {
        sendEffect(
            AddEditDiaryEffect.ShowSnackBar(
                SnackBarData(
                    title = UiText.StringRes(titleStringResource),
                    message = UiText.StringRes(messageStringResource),
                    isError = isError
                )
            )
        )
    }
}
