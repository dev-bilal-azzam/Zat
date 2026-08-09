package com.devbilal.presentation.features.diary.screens.attachments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.common.components.AudioPlayer

@Composable
fun AudioAttachmentItem(
    filePath: String,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var currentMillis by remember { mutableStateOf(0L) }
    var totalMillis by remember { mutableStateOf(0L) }

    AudioPlayer(
        url = filePath,
        play = isPlaying,
        onProgressUpdate = { p, c, t ->
            progress = p
            currentMillis = c
            totalMillis = t
        }
    )

    Column(
        modifier = modifier.fillMaxSize().padding(Theme.spacing._24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatMillis(currentMillis) + " / " + formatMillis(totalMillis),
            style = Theme.typography.body.large,
            color = Theme.colorScheme.primary.onPrimaryBody
        )

        Spacer(modifier = Modifier.height(Theme.spacing._16))

        // Custom Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Theme.colorScheme.disabled)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(Theme.colorScheme.primary.primary)
            )
        }

        Spacer(modifier = Modifier.height(Theme.spacing._24))

        PrimaryButton(
            text = if (isPlaying) "Pause" else "Play",
            onClick = { isPlaying = !isPlaying }
        )
    }
}

private fun formatMillis(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
