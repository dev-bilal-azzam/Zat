package com.devbilal.presentation.features.diary.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.divider.HorizontalDivider
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.features.diary.common.components.SettingsItem
import com.devbilal.presentation.features.diary.common.components.SettingsSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_selected
import zat.presentation.generated.resources.theme

@Composable
fun ThemeSection(
    modifier: Modifier = Modifier,
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    SettingsSection(
        title = stringResource(Res.string.theme),
        modifier = modifier
    ) {
        Column {
            AppTheme.entries.forEachIndexed { index, theme ->
                val tint = when (theme) {
                    AppTheme.DARK -> Theme.colorScheme.primary.primary
                    AppTheme.LIGHT -> Theme.colorScheme.shadeSecondary
                    AppTheme.SYSTEM -> Theme.colorScheme.shadeTertiary
                }
                SettingsItem(
                    icon = vectorResource(theme.iconRes),
                    iconTint = tint,
                    title = stringResource(theme.nameRes),
                    onClick = { onThemeSelected(theme) },
                    showIconBackground = false,
                    trailingIconTint = Theme.colorScheme.primary.primary,
                    trailingIcon = if (selectedTheme == theme) vectorResource(Res.drawable.ic_selected) else null,
                    trailingIconAutoMirror = false
                )

                if (index < AppTheme.entries.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 1.dp,
                        color = Theme.colorScheme.border.disabled
                    )
                }
            }
        }
    }
}
