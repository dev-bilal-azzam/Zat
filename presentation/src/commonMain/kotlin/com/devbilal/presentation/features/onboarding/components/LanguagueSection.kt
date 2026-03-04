package com.devbilal.presentation.features.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.devbilal.designsystem.component.button.radioButton.RadioButton
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.arabic_language
import zat.presentation.generated.resources.choose_language
import zat.presentation.generated.resources.default_language
import zat.presentation.generated.resources.ic_glob

@Composable
fun LanguageSection(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconTextRow(
            icon = painterResource(Res.drawable.ic_glob),
            text = stringResource(Res.string.choose_language)
        )

        AppLanguage.entries.forEach { entry ->
            val hint =
                if (entry == AppLanguage.Arabic)
                    stringResource(Res.string.arabic_language)
                else
                    stringResource(Res.string.default_language)

            RadioButton(
                isSelected = selectedLanguage == entry,
                onClick = { onLanguageSelected(entry) },
                label = entry.name,
                hint = hint,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
@Preview
fun PreviewLanguageSection() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        Box(
            modifier = Modifier.background(Theme.colorScheme.background.surfaceLow)
        ) {
            LanguageSection(
                selectedLanguage = language,
                onLanguageSelected = { language = it }
            )
        }
    }
}