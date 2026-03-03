package com.devbilal.presentation.features.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.IconTextRow
import com.devbilal.designsystem.component.button.radioButton.BoxRadioButton
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.appearance
import zat.presentation.generated.resources.ic_appearance
import zat.presentation.generated.resources.ic_dark_theme
import zat.presentation.generated.resources.ic_light_theme
import zat.presentation.generated.resources.ic_system_theme

@Composable
fun AppearanceSection(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconTextRow(
            icon = painterResource(Res.drawable.ic_appearance),
            text = stringResource(Res.string.appearance)
        )

        AppTheme.entries.forEach { entry ->
            val iconRes = when (entry) {
                AppTheme.DARK -> Res.drawable.ic_dark_theme
                AppTheme.LIGHT -> Res.drawable.ic_light_theme
                AppTheme.SYSTEM -> Res.drawable.ic_system_theme
            }
            BoxRadioButton(
                isSelected = selectedTheme == entry,
                onClick = { onThemeSelected(entry) },
                label = entry.name,
                icon = painterResource(iconRes)
            )
        }
    }
}