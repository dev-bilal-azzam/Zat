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
import com.devbilal.domain.entity.DiaryEntrySummary
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.*

@Composable
fun TodayEntryCard(
    entry: DiaryEntrySummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Color(entry.color.value))
            .clickable { onClick() }
            .padding(Theme.spacing._24),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing._12)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.todays_entry_badge),
                style = Theme.typography.label.medium,
                color = Theme.colorScheme.primary.onPrimary
            )
            
            if (entry.historyCount > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_clock),
                        modifier = Modifier.size(Theme.spacing._16),
                        tint = Theme.colorScheme.primary.onPrimary
                    )
                    Text(
                        text = entry.historyCount.toString(),
                        style = Theme.typography.label.medium,
                        color = Theme.colorScheme.primary.onPrimary
                    )
                }
            }
        }

        Text(
            text = entry.title,
            style = Theme.typography.title.large,
            color = Theme.colorScheme.shadePrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        if (entry.firstImageUrl != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Theme.spacing._32 * 4)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.background.surfaceLow)
            )
        }
    }
}
