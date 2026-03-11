package com.devbilal.presentation.features.auth.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.IconTextRow
import com.devbilal.designsystem.component.button.radioButton.BoxRadioButton
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.appearance
import zat.presentation.generated.resources.ic_appearance

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
            icon = vectorResource(Res.drawable.ic_appearance),
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
                    label = stringResource(entry.nameRes),
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