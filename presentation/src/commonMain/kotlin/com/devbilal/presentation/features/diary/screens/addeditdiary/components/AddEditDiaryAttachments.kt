package com.devbilal.presentation.features.diary.screens.addeditdiary.components

import androidx.compose.foundation.background
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
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.attachment),
            style = Theme.typography.label.extraSmall,
            color = Theme.colorScheme.shadeTertiary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AttachmentItem(
                icon = Res.drawable.ic_add_image,
                label = stringResource(Res.string.image),
                modifier = Modifier.weight(1f)
            )
            AttachmentItem(
                icon = Res.drawable.ic_video,
                label = stringResource(Res.string.video),
                modifier = Modifier.weight(1f)
            )
            AttachmentItem(
                icon = Res.drawable.ic_mic,
                label = stringResource(Res.string.audio),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AttachmentItem(
    icon: DrawableResource,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = null,
            tint = Theme.colorScheme.primary.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = Theme.typography.label.extraSmall,
            color = Theme.colorScheme.shadeSecondary
        )
    }
}
