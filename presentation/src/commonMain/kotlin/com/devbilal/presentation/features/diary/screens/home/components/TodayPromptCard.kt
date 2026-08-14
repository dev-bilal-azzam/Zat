package com.devbilal.presentation.features.diary.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.*

@Composable
fun TodayPromptCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.colorScheme.primary.primary.copy(alpha = 0.8f))
            .clickable { onClick() }
            .padding(Theme.spacing._24)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing._16)
        ) {
            Text(
                text = stringResource(Res.string.how_was_your_day_hint),
                style = Theme.typography.title.large,
                color = Theme.colorScheme.shadePrimary
            )
            
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(Theme.radius.full))
                    .background(Theme.colorScheme.background.surface.copy(alpha = 0.2f))
                    .padding(horizontal = Theme.spacing._16, vertical = Theme.spacing._8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.write_today_entry),
                    style = Theme.typography.label.large,
                    color = Theme.colorScheme.shadePrimary
                )
            }
        }
    }
}
