@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.addeditdiary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.datetime.DatePicker
import com.devbilal.designsystem.component.richtext.RichTextEditor
import com.devbilal.designsystem.component.richtext.RichTextPanel
import com.devbilal.designsystem.component.richtext.rememberRichTextState
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.entity.Attachment
import com.devbilal.domain.entity.AttachmentType
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.media.rememberCameraLauncher
import com.devbilal.presentation.common.media.rememberMediaUtils
import com.devbilal.presentation.common.media.rememberVideoLauncher
import com.devbilal.presentation.common.media.rememberVoiceRecorder
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.common.permission.Permission
import com.devbilal.presentation.common.permission.rememberPermissionHandler
import com.devbilal.presentation.features.diary.common.navigation.navigateToAttachments
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryAppBar
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryAttachments
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryCategorize
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AddEditDiaryHeader
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AttachmentBottomSheet
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AttachmentOption
import com.devbilal.presentation.features.diary.screens.addeditdiary.components.AttachmentPreview
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sv.lib.squircleshape.SquircleShape
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.add_audio
import zat.presentation.generated.resources.add_image
import zat.presentation.generated.resources.add_video
import zat.presentation.generated.resources.browse_your_existing_audio
import zat.presentation.generated.resources.browse_your_existing_media
import zat.presentation.generated.resources.capture_photo
import zat.presentation.generated.resources.choose_from_gallery
import zat.presentation.generated.resources.entry_title_hint
import zat.presentation.generated.resources.how_was_your_day_hint
import zat.presentation.generated.resources.ic_add_image
import zat.presentation.generated.resources.ic_mic
import zat.presentation.generated.resources.ic_video
import zat.presentation.generated.resources.record_audio
import zat.presentation.generated.resources.record_video
import zat.presentation.generated.resources.select_audio_from_files
import zat.presentation.generated.resources.select_from_existing_photos
import zat.presentation.generated.resources.select_video_from_gallery
import zat.presentation.generated.resources.take_new_photo_with_camera
import zat.presentation.generated.resources.use_camera_to_capture_new_video
import zat.presentation.generated.resources.use_mic_to_record_new_audio
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun AddEditDiaryScreen(
    viewModel: AddEditDiaryViewModel = koinViewModel()
) {
    val snackBar = LocalSnackBarHostController.current
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()
    val scope = rememberCoroutineScope()

    val permissionHandler = rememberPermissionHandler()

    val mediaUtils = rememberMediaUtils()
    val voiceRecorder = rememberVoiceRecorder()

    val cameraLauncher = rememberCameraLauncher(
        onResult = { filePath: String? ->
            filePath?.let {
                viewModel.handleIntent(AddEditDiaryIntent.OnAddAttachment(Attachment.Image(filePath = it)))
            }
        }
    )

    val videoLauncher = rememberVideoLauncher(
        onResult = { filePath: String?, thumbnail: ByteArray? ->
            if (filePath != null && thumbnail != null) {
                viewModel.handleIntent(
                    AddEditDiaryIntent.OnAddAttachment(
                        Attachment.Video(
                            filePath = filePath,
                            thumbnail = thumbnail
                        )
                    )
                )
            }
        }
    )

    val imagePickerLauncher = rememberFilePickerLauncher(
        type = PickerType.Image,
        mode = PickerMode.Single,
        onResult = { file: PlatformFile? ->
            file?.let {
                scope.launch(Dispatchers.IO) {
                    viewModel.handleIntent(AddEditDiaryIntent.OnProcessingStarted(AttachmentType.IMAGE))
                    try {
                        val tempFilePath = mediaUtils.platformFileToTempFile(it)
                        viewModel.handleIntent(
                            AddEditDiaryIntent.OnAddAttachment(
                                Attachment.Image(
                                    filePath = tempFilePath
                                )
                            )
                        )
                    } catch (e: Exception) {
                        viewModel.handleIntent(AddEditDiaryIntent.OnProcessingFailed(e.message ?: ""))
                    }
                }
            }
        }
    )

    val videoPickerLauncher = rememberFilePickerLauncher(
        type = PickerType.Video,
        mode = PickerMode.Single,
        onResult = { file: PlatformFile? ->
            file?.let {
                scope.launch(Dispatchers.IO) {
                    viewModel.handleIntent(AddEditDiaryIntent.OnProcessingStarted(AttachmentType.VIDEO))
                    try {
                        val tempFilePath = mediaUtils.platformFileToTempFile(it)
                        val thumbnail = mediaUtils.generateThumbnail(tempFilePath)
                        viewModel.handleIntent(
                            AddEditDiaryIntent.OnAddAttachment(
                                Attachment.Video(
                                    filePath = tempFilePath,
                                    thumbnail = thumbnail ?: byteArrayOf()
                                )
                            )
                        )
                    } catch (e: Exception) {
                        viewModel.handleIntent(AddEditDiaryIntent.OnProcessingFailed(e.message ?: ""))
                    }
                }
            }
        }
    )

    val audioPickerLauncher = rememberFilePickerLauncher(
        type = PickerType.File(),
        mode = PickerMode.Single,
        onResult = { file: PlatformFile? ->
            file?.let {
                scope.launch(Dispatchers.IO) {
                    viewModel.handleIntent(AddEditDiaryIntent.OnProcessingStarted(AttachmentType.AUDIO))
                    try {
                        val tempFilePath = mediaUtils.platformFileToTempFile(it)
                        viewModel.handleIntent(
                            AddEditDiaryIntent.OnAddAttachment(
                                Attachment.Audio(
                                    filePath = tempFilePath
                                )
                            )
                        )
                    } catch (e: Exception) {
                        viewModel.handleIntent(AddEditDiaryIntent.OnProcessingFailed(e.message ?: ""))
                    }
                }
            }
        }
    )

    viewModel.ObserveEffects { effect ->
        when (effect) {
            AddEditDiaryEffect.NavigateBack -> navigator.navigateBack()
            is AddEditDiaryEffect.ShowSnackBar -> {
                snackBar.showSnackBar(effect.snackBarData)
            }

            AddEditDiaryEffect.LaunchCamera -> {
                permissionHandler.askPermission(Permission.CAMERA) { isGranted ->
                    if (isGranted) {
                        scope.launch(Dispatchers.IO) { cameraLauncher.launch() }
                    }
                }
            }

            AddEditDiaryEffect.LaunchVideoRecorder -> {
                permissionHandler.askPermission(Permission.CAMERA) { isGranted ->
                    if (isGranted) {
                        scope.launch(Dispatchers.IO) { videoLauncher.launch() }
                    }
                }
            }

            AddEditDiaryEffect.LaunchAudioRecorder -> {
                permissionHandler.askPermission(Permission.RECORD_AUDIO) { isGranted ->
                    if (isGranted) {
                        scope.launch(Dispatchers.IO) {
                            voiceRecorder.onResult { filePath ->
                                viewModel.handleIntent(
                                    AddEditDiaryIntent.OnAddAttachment(
                                        Attachment.Audio(
                                            filePath = filePath
                                        )
                                    )
                                )
                            }
                            viewModel.handleIntent(AddEditDiaryIntent.OnStartRecordAudio)
                            voiceRecorder.startRecording()
                        }
                    }
                }
            }

            AddEditDiaryEffect.StopAudioRecorder -> {
                voiceRecorder.stopRecording()
            }

            AddEditDiaryEffect.LaunchAudioPicker -> {
                audioPickerLauncher.launch()
            }

            AddEditDiaryEffect.LaunchImagePicker -> {
                imagePickerLauncher.launch()
            }

            AddEditDiaryEffect.LaunchVideoPicker -> {
                videoPickerLauncher.launch()
            }

            is AddEditDiaryEffect.NavigateToAttachments -> navigator.navigateToAttachments(
                effect.entryId,
                effect.initialIndex
            )
        }
    }

    AddEditDiaryScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun AddEditDiaryScreenContent(
    state: AddEditDiaryState,
    onIntent: (AddEditDiaryIntent) -> Unit
) {
    val density = LocalDensity.current
    var footerHeight by remember { mutableStateOf(0.dp) }
    val panelBottomPadding = (currentKeyboardHeight() - footerHeight - 32.dp).coerceAtLeast(0.dp)

    val richTextState = rememberRichTextState()

    LaunchedEffect(state.content) {
        if (state.content.isNotEmpty() && richTextState.toHtml() != state.content) {
            richTextState.setHtml(state.content)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AddEditDiaryAppBar(
                isEditMode = state.isEditMode,
                isSaveEnabled = !state.isPickingAttachments,
                onBackClicked = { onIntent(AddEditDiaryIntent.OnBackClicked) },
                onSaveClick = {
                    onIntent(AddEditDiaryIntent.OnContentChanged(richTextState.toHtml()))
                    onIntent(AddEditDiaryIntent.OnSaveClicked)
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AddEditDiaryHeader(
                    date = state.date,
                    onDateClicked = { onIntent(AddEditDiaryIntent.OnPickDateClicked) }
                )

                BasicTextField(
                    value = state.title,
                    onValueChange = { onIntent(AddEditDiaryIntent.OnTitleChanged(it)) },
                    textStyle = Theme.typography.title.large.copy(color = Theme.colorScheme.shadePrimary),
                    cursorBrush = SolidColor(Theme.colorScheme.primary.primary),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (state.title.isEmpty()) {
                            Text(
                                text = stringResource(Res.string.entry_title_hint),
                                style = Theme.typography.title.large,
                                color = Theme.colorScheme.shadeTertiary
                            )
                        }
                        innerTextField()
                    }
                )


                RichTextEditor(
                    state = richTextState,
                    placeholder = stringResource(Res.string.how_was_your_day_hint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                RichTextPanel(
                    state = richTextState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = panelBottomPadding)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            footerHeight = with(density) { coordinates.size.height.toDp() }
                        }
                ) {
                    AttachmentPreview(
                        attachments = state.attachments,
                        onRemoveAttachment = { onIntent(AddEditDiaryIntent.OnRemoveAttachment(it)) },
                        onClick = { onIntent(AddEditDiaryIntent.OnAttachmentClicked(it)) },
                    )

                    AddEditDiaryAttachments(
                        onAttachImageClicked = { onIntent(AddEditDiaryIntent.OnAttachImageClicked) },
                        onAttachVideoClicked = { onIntent(AddEditDiaryIntent.OnAttachVideoClicked) },
                        onAttachAudioClicked = { onIntent(AddEditDiaryIntent.OnAttachAudioClicked) },
                        isPicking = state.isPickingAttachments
                    )

                    AddEditDiaryCategorize(
                        selectedColor = state.color,
                        onColorSelected = { onIntent(AddEditDiaryIntent.OnColorChanged(it)) }
                    )
                }

            }

            if (state.isDatePickerVisible) {
                DatePicker(
                    onDateSelected = { onIntent(AddEditDiaryIntent.OnDateChanged(it)) },
                    onDismissRequest = { onIntent(AddEditDiaryIntent.OnDismissDatePicker) }
                )
            }

            if (state.isAttachImageOverlayVisible) {
                AttachmentBottomSheet(
                    title = stringResource(Res.string.add_image),
                    onDismissRequest = { onIntent(AddEditDiaryIntent.OnDismissAttachImageOverlay) },
                    options = listOf(
                        AttachmentOption(
                            title = stringResource(Res.string.choose_from_gallery),
                            description = stringResource(Res.string.select_from_existing_photos),
                            icon = Res.drawable.ic_add_image,
                            onClick = { onIntent(AddEditDiaryIntent.OnPickImageClicked) }
                        ),
                        AttachmentOption(
                            title = stringResource(Res.string.capture_photo),
                            description = stringResource(Res.string.take_new_photo_with_camera),
                            icon = Res.drawable.ic_add_image,
                            onClick = { onIntent(AddEditDiaryIntent.OnCapturePhotoClicked) }
                        )
                    )
                )
            }

            if (state.isAttachVideoOverlayVisible) {
                AttachmentBottomSheet(
                    title = stringResource(Res.string.add_video),
                    onDismissRequest = { onIntent(AddEditDiaryIntent.OnDismissAttachVideoOverlay) },
                    options = listOf(
                        AttachmentOption(
                            title = stringResource(Res.string.select_video_from_gallery),
                            description = stringResource(Res.string.browse_your_existing_media),
                            icon = Res.drawable.ic_video,
                            onClick = { onIntent(AddEditDiaryIntent.OnPickVideoClicked) }
                        ),
                        AttachmentOption(
                            title = stringResource(Res.string.record_video),
                            description = stringResource(Res.string.use_camera_to_capture_new_video),
                            icon = Res.drawable.ic_video,
                            onClick = { onIntent(AddEditDiaryIntent.OnRecordVideoClicked) }
                        )
                    )
                )
            }

            if (state.isAttachAudioOverlayVisible) {
                AttachmentBottomSheet(
                    title = stringResource(Res.string.add_audio),
                    onDismissRequest = { onIntent(AddEditDiaryIntent.OnDismissAttachAudioOverlay) },
                    options = listOf(
                        AttachmentOption(
                            title = stringResource(Res.string.select_audio_from_files),
                            description = stringResource(Res.string.browse_your_existing_audio),
                            icon = Res.drawable.ic_mic,
                            onClick = { onIntent(AddEditDiaryIntent.OnPickAudioClicked) }
                        ),
                        AttachmentOption(
                            title = stringResource(Res.string.record_audio),
                            description = stringResource(Res.string.use_mic_to_record_new_audio),
                            icon = Res.drawable.ic_mic,
                            onClick = { onIntent(AddEditDiaryIntent.OnRecordAudioClicked) }
                        )
                    )
                )
            }
            if (state.isRecordingAudio) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(enabled = false) { /* Block clicks */ },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .background(
                                color = Theme.colorScheme.background.surface,
                                shape = SquircleShape(Theme.radius.md)
                            )
                            .padding(32.dp)
                    ) {
                        Text(
                            text = "Recording Audio...",
                            style = Theme.typography.title.medium,
                            color = Theme.colorScheme.primary.primary
                        )
                        PrimaryButton(
                            text = "Stop Recording",
                            onClick = { onIntent(AddEditDiaryIntent.OnStopRecordAudioClicked) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun currentKeyboardHeight(): Dp {
    val density = LocalDensity.current
    val imeInsets = WindowInsets.ime
    val height = with(density) {
        imeInsets.getBottom(density).toDp()
    }
    return height
}