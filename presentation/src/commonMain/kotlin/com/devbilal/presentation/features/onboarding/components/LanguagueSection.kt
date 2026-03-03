package com.devbilal.presentation.features.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.IconTextRow
import com.devbilal.designsystem.component.button.radioButton.RadioButton
import com.devbilal.designsystem.util.AppLanguage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.*

@Composable
fun LanguageSection(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
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
                hint = hint
            )
        }
    }
}