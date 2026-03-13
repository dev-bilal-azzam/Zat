package com.devbilal.presentation.features.diary.screens.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.divider.HorizontalDivider
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.util.AppLanguage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_selected
import zat.presentation.generated.resources.language

@Composable
fun LanguageSection(
    modifier: Modifier = Modifier,
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    SettingsSection(
        title = stringResource(Res.string.language),
        modifier = modifier
    ) {
        Column {
            AppLanguage.entries.forEachIndexed { index, language ->
                SettingsItem(
                    title = stringResource(language.nameRes),
                    description = stringResource(language.hintRes),
                    onClick = { onLanguageSelected(language) },
                    showIconBackground = false,
                    trailingIconTint = Theme.colorScheme.primary.primary,
                    trailingIcon = if (selectedLanguage == language) vectorResource(Res.drawable.ic_selected) else null,
                    trailingIconAutoMirror = false
                )

                if (index < AppLanguage.entries.size - 1) {
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