package com.devbilal.presentation.features.diary.screens.attachments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.ZatIconButton
import com.devbilal.designsystem.component.slider.ZatSlider
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.common.components.AudioPlayer
import com.devbilal.presentation.features.diary.screens.attachments.AttachmentsIntent
import com.devbilal.presentation.features.diary.screens.attachments.AudioPlaybackState
import org.jetbrains.compose.resources.painterResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_pause
import zat.presentation.generated.resources.ic_play
import zat.presentation.generated.resources.ic_stop

@Composable
fun AudioAttachmentItem(
    filePath: String,
    state: AudioPlaybackState,
    onIntent: (AttachmentsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isSeeking = state.seekToPosition != null
    
    var sliderWidth by remember { mutableStateOf(0) }
    var localSliderValue by remember { mutableStateOf(0f) }
    
    // Synchronize local value with state when not dragging and not seeking
    LaunchedEffect(state.currentPosition, isDragged, isSeeking) {
        if (!isDragged && !isSeeking) {
            localSliderValue = if (state.totalDuration > 0) {
                state.currentPosition.toFloat() / state.totalDuration
            } else {
                0f
            }
        }
    }

    AudioPlayer(
        url = filePath,
        play = state.isPlaying,
        seekTo = state.seekToPosition,
        onProgressUpdate = { _, current, total ->
            onIntent(AttachmentsIntent.UpdateAudioProgress(current, total))
        },
        onCompletion = {
            onIntent(AttachmentsIntent.OnAudioPlaybackCompleted)
        }
    )

    Column(
        modifier = modifier.fillMaxSize().padding(Theme.spacing._24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val displayPosition = if (isDragged) {
            (localSliderValue * state.totalDuration).toLong()
        } else {
            state.currentPosition
        }

        Text(
            text = formatMillis(displayPosition) + " / " + formatMillis(state.totalDuration),
            style = Theme.typography.body.large,
            color = Theme.colorScheme.primary.onPrimaryBody
        )

        Spacer(modifier = Modifier.height(Theme.spacing._32))

        Box(modifier = Modifier.fillMaxWidth()) {
            // Tooltip
            if (isDragged) {
                val thumbOffset = remember(localSliderValue, sliderWidth) {
                    (localSliderValue * sliderWidth).toInt()
                }
                
                Box(
                    modifier = Modifier
                        .offset { IntOffset(x = thumbOffset, y = 0) }
                        .align(Alignment.TopStart)
                        .offset(y = (-32).dp) // Above the slider
                        .background(
                            color = Theme.colorScheme.primary.primary,
                            shape = RoundedCornerShape(Theme.radius.xs)
                        )
                        .padding(horizontal = Theme.spacing._8, vertical = Theme.spacing._4)
                ) {
                    Text(
                        text = formatMillis((localSliderValue * state.totalDuration).toLong()),
                        style = Theme.typography.label.extraSmall,
                        color = Theme.colorScheme.primary.onPrimary
                    )
                }
            }

            ZatSlider(
                value = localSliderValue,
                onValueChange = { localSliderValue = it },
                onValueChangeFinished = {
                    onIntent(AttachmentsIntent.SeekAudioTo((localSliderValue * state.totalDuration).toLong()))
                },
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        sliderWidth = coordinates.size.width
                    }
            )
        }

        Spacer(modifier = Modifier.height(Theme.spacing._24))

        Row(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ZatIconButton(
                painter = painterResource(if (state.isPlaying) Res.drawable.ic_pause else Res.drawable.ic_play),
                onClick = { onIntent(AttachmentsIntent.ToggleAudioPlayback) }
            )

            ZatIconButton(
                painter = painterResource(Res.drawable.ic_stop),
                onClick = { onIntent(AttachmentsIntent.StopAudioPlayback) },
                containerColor = Theme.colorScheme.error,
                contentColor = Color.White
            )
        }
    }
}

private fun formatMillis(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
