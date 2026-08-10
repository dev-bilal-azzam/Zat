package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.indicator.DotsProgressIndicator
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.attachment
import zat.presentation.generated.resources.audio
import zat.presentation.generated.resources.ic_add_image
import zat.presentation.generated.resources.ic_mic
import zat.presentation.generated.resources.ic_video
import zat.presentation.generated.resources.image
import zat.presentation.generated.resources.video

@Composable
fun AddEditDiaryAttachments(
    onAttachImageClicked: () -> Unit,
    onAttachVideoClicked: () -> Unit,
    onAttachAudioClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isPicking: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.attachment),
                style = Theme.typography.label.extraSmall,
                color = Theme.colorScheme.shadeTertiary
            )

            if (isPicking) {
                DotsProgressIndicator(
                    numberOfDots = 3,
                    dotSize = 4.dp,
                    spaceBetween = 2.dp
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AttachmentItem(
                icon = Res.drawable.ic_add_image,
                label = stringResource(Res.string.image),
                modifier = Modifier.weight(1f),
                onClick = onAttachImageClicked,
                isEnabled = !isPicking
            )
            AttachmentItem(
                icon = Res.drawable.ic_video,
                label = stringResource(Res.string.video),
                modifier = Modifier.weight(1f),
                onClick = onAttachVideoClicked,
                isEnabled = !isPicking
            )
            AttachmentItem(
                icon = Res.drawable.ic_mic,
                label = stringResource(Res.string.audio),
                modifier = Modifier.weight(1f),
                onClick = onAttachAudioClicked,
                isEnabled = !isPicking
            )
        }
    }
}

@Composable
private fun AttachmentItem(
    icon: DrawableResource,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(
                if (isEnabled) Theme.colorScheme.background.surface
                else Theme.colorScheme.background.surface.copy(alpha = 0.5f)
            )
            .clickable(enabled = isEnabled) { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isEnabled) Theme.colorScheme.primary.primary else Theme.colorScheme.disabled,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = Theme.typography.label.extraSmall,
            color = if (isEnabled) Theme.colorScheme.shadeSecondary else Theme.colorScheme.disabled
        )
    }
}
