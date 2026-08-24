package com.devbilal.presentation.features.diary.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.entity.AttachmentType
import com.devbilal.domain.entity.DiaryEntrySummary
import org.jetbrains.compose.resources.painterResource
import zat.presentation.generated.resources.*

@Composable
fun RecentEntryCard(
    entry: DiaryEntrySummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surface)
            .clickable { onClick() }
            .padding(Theme.spacing._16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._16)
    ) {
        // Color indicator
        Box(
            modifier = Modifier
                .width(Theme.spacing._4)
                .height(Theme.spacing._32 + Theme.spacing._8)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Color(entry.color.value))
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing._4)
        ) {
            Text(
                text = entry.title,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.shadePrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = entry.date.toString(),
                style = Theme.typography.body.small,
                color = Theme.colorScheme.shadeTertiary
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8)
        ) {
            if (entry.historyCount > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_clock),
                        modifier = Modifier.size(Theme.spacing._16),
                        tint = Theme.colorScheme.shadeTertiary
                    )
                    Text(
                        text = entry.historyCount.toString(),
                        style = Theme.typography.label.medium,
                        color = Theme.colorScheme.shadeTertiary
                    )
                }
            }

            entry.attachmentTypes.distinct().forEach { type ->
                val icon = when (type) {
                    AttachmentType.IMAGE -> Res.drawable.ic_add_image
                    AttachmentType.VIDEO -> Res.drawable.ic_video
                    AttachmentType.AUDIO -> Res.drawable.ic_mic
                }
                Icon(
                    painter = painterResource(icon),
                    modifier = Modifier.size(Theme.spacing._16),
                    tint = Theme.colorScheme.shadeTertiary
                )
            }
        }
    }
}
