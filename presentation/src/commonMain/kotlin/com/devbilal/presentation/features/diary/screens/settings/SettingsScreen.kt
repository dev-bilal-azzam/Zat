package com.devbilal.presentation.features.diary.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.divider.HorizontalDivider
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.features.diary.common.navigation.LocalNavigator
import com.devbilal.presentation.features.diary.common.navigation.navigateToSecurity
import com.devbilal.presentation.features.diary.screens.settings.components.BackupSection
import com.devbilal.presentation.features.diary.screens.settings.components.LanguageSection
import com.devbilal.presentation.features.diary.screens.settings.components.SettingsFooter
import com.devbilal.presentation.features.diary.screens.settings.components.SettingsHeader
import com.devbilal.presentation.features.diary.screens.settings.components.SettingsItem
import com.devbilal.presentation.features.diary.screens.settings.components.SettingsSection
import com.devbilal.presentation.features.diary.screens.settings.components.ThemeSection
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_arrow_right_ios
import zat.presentation.generated.resources.ic_lock
import zat.presentation.generated.resources.security
import zat.presentation.generated.resources.security_settings
import zat.presentation.generated.resources.security_settings_desc
import zat.presentation.generated.resources.settings

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            SettingsEffect.NavigateBack -> navigator.navigateBack()
            SettingsEffect.NavigateToSecuritySettings -> navigator.navigateToSecurity()
        }
    }

    SettingsScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SettingsScreenContent(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.settings),
                onLeadingClick = { onIntent(SettingsIntent.OnBackClicked) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            SettingsHeader()

            SettingsSection(title = stringResource(Res.string.security)) {
                SettingsItem(
                    icon = vectorResource(Res.drawable.ic_lock),
                    title = stringResource(Res.string.security_settings),
                    description = stringResource(Res.string.security_settings_desc),
                    onClick = { onIntent(SettingsIntent.OnSecurityClicked) },
                    trailingIcon = vectorResource(Res.drawable.ic_arrow_right_ios)
                )
            }

            ThemeSection(
                selectedTheme = state.selectedTheme,
                onThemeSelected = { onIntent(SettingsIntent.OnThemeSelected(it)) }
            )

            LanguageSection(
                selectedLanguage = state.selectedLanguage,
                onLanguageSelected = { onIntent(SettingsIntent.OnLanguageSelected(it)) }
            )

            BackupSection(
                onExportClicked = { onIntent(SettingsIntent.OnExportClicked) },
                onImportClicked = { onIntent(SettingsIntent.OnImportClicked) }
            )

            HorizontalDivider()

            SettingsFooter(
                onPrivacyPolicyClicked = { onIntent(SettingsIntent.OnPrivacyPolicyClicked) },
                onContactSupportClicked = { onIntent(SettingsIntent.OnContactSupportClicked) }
            )
        }
    }
}

@Composable
@Preview
fun SettingsPreview() {
    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        SettingsScreenContent(
            state = SettingsState(
                selectedLanguage = language,
                selectedTheme = theme
            ),
            onIntent = {}
        )
    }
}
