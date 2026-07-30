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
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.domain.entity.AttachmentType
import com.devbilal.domain.entity.DiaryEntrySummary
import org.jetbrains.compose.resources.painterResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.*

@Composable
fun DiaryEntryItem(
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
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Color indicator
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Color(entry.color.value))
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (entry.historyCount > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_clock),
                        modifier = Modifier.size(16.dp),
                        tint = Theme.colorScheme.shadeTertiary
                    )
                    Text(
                        text = entry.historyCount.toString(),
                        style = Theme.typography.label.small,
                        color = Theme.colorScheme.shadeTertiary
                    )
                }
            }

            entry.attachmentTypes.distinct().forEach { type ->
                println("Attachments -> $type")
                val icon = when (type) {
                    AttachmentType.IMAGE -> Res.drawable.ic_add_image
                    AttachmentType.VIDEO -> Res.drawable.ic_video
                    AttachmentType.AUDIO -> Res.drawable.ic_mic
                }
                Icon(
                    painter = painterResource(icon),
                    modifier = Modifier.size(16.dp),
                    tint = Theme.colorScheme.shadeTertiary
                )
            }
        }

        if (entry.firstImageUrl != null) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(Theme.radius.sm))
                    .background(Theme.colorScheme.background.surfaceLow)
            )
        }
    }
}

@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun DiaryEntryItemPreview() {
    ZatTheme {
        DiaryEntryItem(
            entry = DiaryEntrySummary(
                id = kotlin.uuid.Uuid.random(),
                title = "My Secret Diary Entry",
                date = kotlinx.datetime.LocalDate(2023, 10, 27),
                historyCount = 3,
                attachmentTypes = listOf(AttachmentType.IMAGE, AttachmentType.AUDIO),
                firstImageUrl = null,
                color = com.devbilal.domain.entity.DiaryColor(0xFF4285F4)
            ),
            onClick = {}
        )
    }
}
