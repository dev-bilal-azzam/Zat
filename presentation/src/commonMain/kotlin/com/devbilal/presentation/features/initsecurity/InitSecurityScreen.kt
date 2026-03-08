package com.devbilal.presentation.features.initsecurity

import com.devbilal.presentation.base.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.text.Text
import com.devbilal.presentation.features.initsecurity.components.InitSecurityHeader
import com.devbilal.presentation.features.initsecurity.components.UnlockMethodsSection
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.*


@Composable
fun InitSecurityScreen(
    viewModel: InitSecurityViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSetupPin: () -> Unit,
    onNavigateToSetupPattern: () -> Unit
) {
    viewModel.ObserveEffects {
        when (it) {
            InitSecurityEffect.NavigateBack -> onNavigateBack()
            InitSecurityEffect.NavigateToHome -> onNavigateToHome()
            InitSecurityEffect.NavigateToSetPattern -> onNavigateToSetupPattern()
            InitSecurityEffect.NavigateToSetPin -> onNavigateToSetupPin()
        }
    }

    InitSecurityScreenContent(
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun InitSecurityScreenContent(
    onIntent: (InitSecurityIntent) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.security_setup),
                onLeadingClick = { onIntent(InitSecurityIntent.OnBackClicked) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                InitSecurityHeader()
                UnlockMethodsSection(
                    onPinClicked = { onIntent(InitSecurityIntent.OnPinClicked) },
                    onPatternClicked = { onIntent(InitSecurityIntent.OnPatternClicked) }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(
                    text = stringResource(Res.string.skip),
                    onClick = { onIntent(InitSecurityIntent.OnSetupLaterClicked) },
                    modifier = Modifier.fillMaxWidth()
                )


                Text(
                    text = stringResource(Res.string.setup_later),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.shadeTertiary,
                    textAlign = TextAlign.Center
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

        InitSecurityScreenContent()

    }
}