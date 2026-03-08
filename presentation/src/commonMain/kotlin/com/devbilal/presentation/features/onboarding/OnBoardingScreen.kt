package com.devbilal.presentation.features.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.features.onboarding.components.AppearanceSection
import com.devbilal.presentation.features.onboarding.components.LanguageSection
import com.devbilal.presentation.features.onboarding.components.OnBoardingHeader
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.`continue`
import zat.presentation.generated.resources.ic_arrow_right


@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    onNavigateToInitSecurity: () -> Unit
) {
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when(it) {
            OnBoardingEffect.NavigateToInitSecurity -> { onNavigateToInitSecurity() }
        }
    }

    OnBoardingScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun OnBoardingScreenContent(
    state: OnBoardingState,
    onIntent: (OnBoardingIntent) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBoardingHeader()

                LanguageSection(
                    selectedLanguage = state.selectedLanguage,
                    onLanguageSelected = { onIntent(OnBoardingIntent.OnLanguageSelected(it)) },
                    modifier = Modifier.align(Alignment.Start)
                )

                AppearanceSection(
                    selectedTheme = state.selectedTheme,
                    onThemeSelected = { onIntent(OnBoardingIntent.OnThemeSelected(it)) },
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            PrimaryButton(
                text = stringResource(Res.string.`continue`),
                trailingIcon = vectorResource(Res.drawable.ic_arrow_right),
                onClick = { onIntent(OnBoardingIntent.OnContinueClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.BottomCenter)
            )
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

        OnBoardingScreenContent(OnBoardingState())

    }
}