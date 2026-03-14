package com.devbilal.presentation.features.auth.screens.setuppin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.devbilal.presentation.common.components.Numpad
import com.devbilal.presentation.common.components.PinIndicator
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.auth.screens.setuppin.components.SetupPinHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.confirm
import zat.presentation.generated.resources.create_your_pin
import zat.presentation.generated.resources.pin_usage_message


@Composable
fun SetupPinScreen(
    viewModel: SetupPinViewModel = koinViewModel(),
    onSuccessfulSetup: () -> Unit
) {
    val snackBarHost = LocalSnackBarHostController.current
    val state = viewModel.collectState()
    val navigator = LocalNavigator.current

    viewModel.ObserveEffects {
        when (it) {
            SetupPinEffect.NavigateBack -> navigator.navigateBack()
            SetupPinEffect.SuccessfulSetup -> { onSuccessfulSetup() }
            is SetupPinEffect.ShowSnackBar -> snackBarHost.showSnackBar(it.snackBarData)
        }
    }

    SetupPinScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SetupPinScreenContent(
    state: SetupPinState,
    onIntent: (SetupPinIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.create_your_pin),
                onLeadingClick = { onIntent(SetupPinIntent.OnBackClicked) }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                SetupPinHeader()

                PinIndicator(pin = state.pin)
            }

            Numpad(
                onNumberClick = { onIntent(SetupPinIntent.OnNumberClicked(it)) },
                onBackspaceClick = { onIntent(SetupPinIntent.OnBackspaceClicked) }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(
                    text = stringResource(Res.string.confirm),
                    onClick = { onIntent(SetupPinIntent.OnConfirmClicked) },
                    isEnabled = state.isConfirmEnabled,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(Res.string.pin_usage_message),
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

        SetupPinScreenContent(
            state = SetupPinState(),
            onIntent = {}
        )

    }
}
