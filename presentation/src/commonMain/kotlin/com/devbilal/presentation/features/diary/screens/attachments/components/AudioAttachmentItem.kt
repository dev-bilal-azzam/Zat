package com.devbilal.presentation.features.diary.screens.attachments.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    AudioPlayer(
        url = filePath,
        play = state.isPlaying,
        seekTo = state.seekToPosition,
        onProgressUpdate = { _, current, total ->
            onIntent(AttachmentsIntent.UpdateAudioProgress(current, total))
        }
    )

    Column(
        modifier = modifier.fillMaxSize().padding(Theme.spacing._24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatMillis(state.currentPosition) + " / " + formatMillis(state.totalDuration),
            style = Theme.typography.body.large,
            color = Theme.colorScheme.primary.onPrimaryBody
        )

        Spacer(modifier = Modifier.height(Theme.spacing._16))

        ZatSlider(
            value = if (state.totalDuration > 0) state.currentPosition.toFloat() / state.totalDuration else 0f,
            onValueChange = { progress ->
                onIntent(AttachmentsIntent.SeekAudioTo((progress * state.totalDuration).toLong()))
            },
            modifier = Modifier.fillMaxWidth()
        )

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
