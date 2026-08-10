package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.ZatIconButton
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_close_circle
import zat.presentation.generated.resources.ic_pause
import zat.presentation.generated.resources.ic_play
import zat.presentation.generated.resources.ic_stop

@Composable
fun AudioRecordingDialog(
    durationMs: Long,
    isPaused: Boolean,
    amplitudeList: List<Float>,
    onPauseResume: () -> Unit,
    onStop: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .width(300.dp)
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.colorScheme.background.surface)
                .padding(Theme.spacing._24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.spacing._24)
        ) {
            Text(
                text = if (isPaused) "Recording Paused" else "Recording...",
                style = Theme.typography.title.medium,
                color = if (isPaused) Theme.colorScheme.shadeSecondary else Theme.colorScheme.error
            )

            // Pulsing indicator / Waveform
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!isPaused) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        amplitudeList.forEach { amplitude ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 1.dp)
                                    .width(2.dp)
                                    .height(maxOf(4.dp, (amplitude * 60).dp))
                                    .background(Theme.colorScheme.primary.primary, CircleShape)
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Theme.colorScheme.shadeTertiary, CircleShape)
                    )
                }
            }

            Text(
                text = formatMillis(durationMs),
                style = Theme.typography.headline.large,
                color = Theme.colorScheme.shadePrimary
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing._16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Cancel
                ZatIconButton(
                    painter = painterResource(Res.drawable.ic_close_circle),
                    onClick = onCancel,
                    containerColor = Theme.colorScheme.background.surfaceLow,
                    contentColor = Theme.colorScheme.shadeSecondary
                )

                // Pause / Resume
                ZatIconButton(
                    painter = painterResource(if (isPaused) Res.drawable.ic_play else Res.drawable.ic_pause),
                    onClick = onPauseResume,
                    containerColor = Theme.colorScheme.primary.primary,
                    contentColor = Color.White
                )

                // Stop
                ZatIconButton(
                    painter = painterResource(Res.drawable.ic_stop),
                    onClick = onStop,
                    containerColor = Theme.colorScheme.error,
                    contentColor = Color.White
                )
            }
        }
    }
}

private fun formatMillis(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
}
