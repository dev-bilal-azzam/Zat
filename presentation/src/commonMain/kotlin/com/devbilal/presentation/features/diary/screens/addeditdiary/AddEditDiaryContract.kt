package com.devbilal.presentation.features.diary.screens.addeditdiary

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.domain.entity.Attachment
import com.devbilal.domain.entity.AttachmentType
import com.devbilal.domain.entity.DiaryColor
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState
import kotlinx.datetime.LocalDate

data class PendingAttachmentUiState(
    val type: AttachmentType,
    val progress: Float? = null
)

data class AddEditDiaryState(
    val id: String? = null,
    val title: String = "",
    val content: String = "",
    val date: LocalDate,
    val color: DiaryColor = DiaryColor.Default,
    val attachments: List<Attachment> = emptyList(),
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val isAttachImageOverlayVisible: Boolean = false,
    val isAttachVideoOverlayVisible: Boolean = false,
    val isAttachAudioOverlayVisible: Boolean = false,
    val isRecordingAudio: Boolean = false,
    val isPickingAttachments: Boolean = false,
    val pendingAttachments: List<PendingAttachmentUiState> = emptyList()
) : UiState

sealed interface AddEditDiaryIntent : UiIntent {
    data object OnBackClicked : AddEditDiaryIntent
    data object OnSaveClicked : AddEditDiaryIntent
    data class OnTitleChanged(val title: String) : AddEditDiaryIntent
    data class OnContentChanged(val content: String) : AddEditDiaryIntent
    data class OnDateChanged(val date: LocalDate) : AddEditDiaryIntent
    data class OnColorChanged(val color: DiaryColor) : AddEditDiaryIntent
    data object OnPickDateClicked : AddEditDiaryIntent
    data object OnDismissDatePicker : AddEditDiaryIntent
    data object OnAttachImageClicked : AddEditDiaryIntent
    data object OnAttachVideoClicked : AddEditDiaryIntent
    data object OnAttachAudioClicked : AddEditDiaryIntent
    data object OnDismissAttachImageOverlay : AddEditDiaryIntent
    data object OnDismissAttachVideoOverlay : AddEditDiaryIntent
    data object OnDismissAttachAudioOverlay : AddEditDiaryIntent
    data object OnCaptureImageClicked : AddEditDiaryIntent
    data object OnRecordVideoClicked : AddEditDiaryIntent
    data object OnRecordAudioClicked : AddEditDiaryIntent
    data object OnStartRecordAudio: AddEditDiaryIntent
    data object OnStopRecordAudioClicked : AddEditDiaryIntent
    data object OnPickImageClicked: AddEditDiaryIntent
    data object OnPickVideoClicked: AddEditDiaryIntent
    data object OnPickAudioClicked: AddEditDiaryIntent
    data class OnAddAttachment(val attachment: Attachment) : AddEditDiaryIntent
    data class OnRemoveAttachment(val attachment: Attachment) : AddEditDiaryIntent
    data class OnAttachmentClicked(val index: Int) : AddEditDiaryIntent
    data class OnProcessingStarted(val type: AttachmentType) : AddEditDiaryIntent
    data class OnProcessingFailed(val error: String) : AddEditDiaryIntent
}

sealed interface AddEditDiaryEffect : UiEffect {
    data object NavigateBack : AddEditDiaryEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : AddEditDiaryEffect
    data object LaunchCamera : AddEditDiaryEffect
    data object LaunchVideoRecorder : AddEditDiaryEffect
    data object LaunchAudioRecorder : AddEditDiaryEffect
    data object StopAudioRecorder : AddEditDiaryEffect
    data object LaunchImagePicker: AddEditDiaryEffect
    data object LaunchVideoPicker: AddEditDiaryEffect
    data object LaunchAudioPicker: AddEditDiaryEffect
    data class NavigateToAttachments(val entryId: String, val initialIndex: Int) : AddEditDiaryEffect
}
