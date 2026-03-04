package com.devbilal.presentation.features.onboarding.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.designsystem.component.*
import com.devbilal.designsystem.theme.theme.*
import org.jetbrains.compose.resources.*
import zat.presentation.generated.resources.*
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.radioButton.BoxRadioButton

@Composable
fun AppearanceSection(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconTextRow(
            icon = painterResource(Res.drawable.ic_appearance),
            text = stringResource(Res.string.appearance)
        )

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AppTheme.entries.forEach { entry ->
                BoxRadioButton(
                    isSelected = selectedTheme == entry,
                    onClick = { onThemeSelected(entry) },
                    label = entry.name,
                    icon = painterResource(entry.iconRes),
                    iconTint = entry.tint,
                    iconBackgroundColor = entry.iconBackgroundColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

}

@Composable
@Preview
fun App() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        Box(
            modifier = Modifier.background(Theme.colorScheme.background.surfaceLow)
        ) {
            AppearanceSection(
                selectedTheme = theme,
                onThemeSelected = { theme = it }
            )
        }
    }
}