package com.devbilal.designsystem.component.appBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.designsystem.generated.resources.*

@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = Theme.colorScheme.shadePrimary,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    leadingContent: (@Composable () -> Unit)? = { BackButton() },
    onLeadingClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding)
    ) {
        leadingContent?.let { content ->
            AppBarOptionContainer(
                onClick = onLeadingClick,
                modifier = Modifier.padding(end = 8.dp),
                content = content
            )
        }
        Text(
            text = title,
            color = titleColor,
            style = Theme.typography.title.medium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )
        trailingContent?.let {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                trailingContent()
            }
        }
    }
}

@Composable
private fun BackButton() {
    Icon(
        imageVector = vectorResource(Res.drawable.ic_arrow_left),
        contentDescription = stringResource(Res.string.back),
        tint = Theme.colorScheme.shadePrimary,
        modifier = Modifier.size(16.dp)
    )
}

@Preview
@Composable
private fun AppBarPreview() {
    ZatTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colorScheme.background.surface),
            contentAlignment = Alignment.Center
        ) {
            AppBar(
                title = "Screen title"
            )
        }
    }
}

@Preview
@Composable
private fun AppBarWithBackNavigationPreview() {
    ZatTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colorScheme.background.surfaceLow),
            contentAlignment = Alignment.Center
        ) {
            AppBar(
                leadingContent = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrow_left_ios),
                        contentDescription = null
                    )
                },
                title = "Screen title",
            )
        }
    }
}

@Preview
@Composable
private fun AppBarWithOptionsPreview() {
    ZatTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colorScheme.background.surfaceLow),
            contentAlignment = Alignment.Center
        ) {
            AppBar(
                leadingContent = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrow_left_ios),
                        contentDescription = null
                    )
                },
                onLeadingClick = {},
                title = "Screen title",
                trailingContent = {
                    AppBarOptionContainer(
                        isBadgeVisible = true,
                        onClick = {},
                        badgeColor = Theme.colorScheme.error
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_user),
                            contentDescription = null
                        )
                    }
                    AppBarOptionContainer(
                        isBadgeVisible = true,
                        onClick = {},
                        badgeColor = Theme.colorScheme.primary.primary
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.checkmark),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}
