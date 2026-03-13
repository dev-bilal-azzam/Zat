package com.devbilal.presentation.features.diary.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme

@Composable
fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: Color = Theme.colorScheme.shadeTertiary,
    boxedIconTint: Color = Theme.colorScheme.primary.primary,
    description: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconTint: Color = Theme.colorScheme.shadeTertiary,
    trailingIconAutoMirror: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    showIconBackground: Boolean = true,
    onClick: () -> Unit = { },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.xl))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (showIconBackground) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.primary.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = boxedIconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = Theme.typography.body.medium,
                color = Theme.colorScheme.shadePrimary
            )
            if (description != null) {
                Text(
                    text = description,
                    style = Theme.typography.label.small,
                    color = Theme.colorScheme.shadeTertiary
                )
            }
        }

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = trailingIconTint,
                modifier = Modifier.size(16.dp),
                autoMirror = trailingIconAutoMirror
            )
        }
    }
}
