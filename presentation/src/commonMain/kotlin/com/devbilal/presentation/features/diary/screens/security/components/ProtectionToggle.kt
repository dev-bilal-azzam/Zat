package com.devbilal.presentation.features.diary.screens.security.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.switch.Switch
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.features.diary.common.components.SettingsItem
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.enable_protection
import zat.presentation.generated.resources.enable_protection_desc
import zat.presentation.generated.resources.ic_lock


@Composable
fun ProtectionToggle(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        SettingsItem(
            title = stringResource(Res.string.enable_protection),
            icon = vectorResource(Res.drawable.ic_lock),
            description = stringResource(Res.string.enable_protection_desc),
            backgroundColor = Theme.colorScheme.background.surface.copy(alpha = .5f),
            showIconBackground = true
        )

        Switch(
            isChecked = isEnabled,
            onCheckedChange = onToggle,
            modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp)
        )
    }
}
