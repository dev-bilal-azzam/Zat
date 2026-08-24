package com.devbilal.presentation.features.diary.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.devbilal.designsystem.component.appBar.AppBarOptionContainer
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.designsystem.component.uitext.asString
import com.devbilal.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_delete
import zat.presentation.generated.resources.ic_search
import zat.presentation.generated.resources.streak_count

@Composable
fun HomeTopHeader(
    greetingRes: StringResource?,
    date: UiText?,
    streakCount: Int,
    onSearchClick: () -> Unit,
    onTrashClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.surfaceLow)
            .padding(Theme.spacing._16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                if (date != null) {
                    Text(
                        text = date.asString(),
                        style = Theme.typography.body.medium,
                        color = Theme.colorScheme.shadeTertiary
                    )
                }
                Spacer(modifier = Modifier.height(Theme.spacing._4))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8)
                ) {
                    greetingRes?.let {
                        Text(
                            text = stringResource(it),
                            style = Theme.typography.title.large,
                            color = Theme.colorScheme.shadePrimary
                        )
                    }

                    // Streak Badge
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Theme.radius.full))
                            .background(Theme.colorScheme.primary.primary.copy(alpha = 0.1f))
                            .padding(horizontal = Theme.spacing._8, vertical = Theme.spacing._2),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4)
                    ) {
                        Text(
                            text = "🔥",
                            style = Theme.typography.label.small
                        )
                        Text(
                            text = stringResource(Res.string.streak_count, streakCount),
                            style = Theme.typography.label.small,
                            color = Theme.colorScheme.primary.primary
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8)
            ) {
                AppBarOptionContainer(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        tint = Theme.colorScheme.shadePrimary,
                        modifier = Modifier.size(Theme.spacing._32)
                    )
                }
                AppBarOptionContainer(onClick = onTrashClick) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        tint = Theme.colorScheme.shadePrimary,
                        modifier = Modifier.size(Theme.spacing._32)
                    )
                }
            }
        }
    }
}
