package com.devbilal.presentation.features.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.features.onboarding.components.AppearanceSection
import com.devbilal.presentation.features.onboarding.components.LanguageSection
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.welcome
import zat.presentation.generated.resources.welcome_message
import zat.presentation.generated.resources.zat
import zat.presentation.generated.resources.zat_logo


@Composable
fun OnBoardingScreen(
    language: AppLanguage,
    theme: AppTheme,
    onLanguageSelected: (AppLanguage) -> Unit,
    onThemeSelected: (AppTheme) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.zat_logo),
                contentDescription = stringResource(Res.string.zat),
                modifier = Modifier.size(240.dp)
            )

            Text(
                text = stringResource(Res.string.welcome),
                style = Theme.typography.headline.large,
                color = Theme.colorScheme.shadePrimary
            )

            Text(
                text = stringResource(Res.string.welcome_message),
                style = Theme.typography.body.medium,
                color = Theme.colorScheme.shadeTertiary
            )

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