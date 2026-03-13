package com.devbilal.presentation.features.diary.screens.security.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_security
import zat.presentation.generated.resources.protection_settings
import zat.presentation.generated.resources.protection_settings_desc


@Composable
fun SecurityHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(Theme.radius.md))
                .background(Theme.colorScheme.primary.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_security),
                contentDescription = null,
                tint = Theme.colorScheme.primary.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = stringResource(Res.string.protection_settings),
            style = Theme.typography.headline.small,
            color = Theme.colorScheme.shadePrimary
        )

        Text(
            text = stringResource(Res.string.protection_settings_desc),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.shadeTertiary
        )
    }
}
