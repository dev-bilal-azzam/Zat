package com.devbilal.presentation.features.auth.screens.initbiometric

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.button.PrimaryButton
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.auth.screens.initbiometric.components.InitBiometricHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.biometric_setup
import zat.presentation.generated.resources.enable
import zat.presentation.generated.resources.skip_for_now

@Composable
fun InitBiometricScreen(
    viewModel: InitBiometricViewModel = koinViewModel(),
    navigateHome: () -> Unit
) {
    val snackBarHost = LocalSnackBarHostController.current
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            InitBiometricEffect.NavigateBack -> navigator.navigateBack()
            InitBiometricEffect.NavigateToHome -> navigateHome()
            is InitBiometricEffect.ShowSnackBar -> snackBarHost.showSnackBar(it.snackBarData)
        }
    }

    InitBiometricScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun InitBiometricScreenContent(
    state: InitBiometricState,
    onIntent: (InitBiometricIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.biometric_setup),
                onLeadingClick = { onIntent(InitBiometricIntent.OnBackClicked) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            InitBiometricHeader(
                modifier = Modifier.weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(
                    text = stringResource(Res.string.enable),
                    onClick = { onIntent(InitBiometricIntent.OnEnableClicked) },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(Res.string.skip_for_now),
                    style = Theme.typography.label.large,
                    color = Theme.colorScheme.shadeTertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onIntent(InitBiometricIntent.OnSkipClicked) }
                        .padding(8.dp)
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

        InitBiometricScreenContent(
            state = InitBiometricState,
            onIntent = {}
        )

    }
}
