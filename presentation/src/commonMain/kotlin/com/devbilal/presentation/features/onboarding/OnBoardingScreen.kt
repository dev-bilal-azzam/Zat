package com.devbilal.presentation.features.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.features.onboarding.components.AppearanceSection
import com.devbilal.presentation.features.onboarding.components.LanguageSection
import com.devbilal.presentation.features.onboarding.components.OnBoardingHeader


@Composable
fun OnBoardingScreen(
    language: AppLanguage,
    theme: AppTheme,
    onLanguageSelected: (AppLanguage) -> Unit,
    onThemeSelected: (AppTheme) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnBoardingHeader()

            LanguageSection(
                selectedLanguage = language,
                onLanguageSelected = onLanguageSelected,
                modifier = Modifier.align(Alignment.Start)
            )

            AppearanceSection(
                selectedTheme = theme,
                onThemeSelected = onThemeSelected,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}


@Composable
@Preview
fun App() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.LIGHT) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

            OnBoardingScreen(
                language = language,
                theme = theme,
                onLanguageSelected = { language = it },
                onThemeSelected = { theme = it }
            )

    }
}