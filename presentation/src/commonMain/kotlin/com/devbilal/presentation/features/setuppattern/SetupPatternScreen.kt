package com.devbilal.presentation.features.setuppattern

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
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
import com.devbilal.presentation.common.navigation.*
import com.devbilal.presentation.features.setuppattern.components.PatternView
import com.devbilal.presentation.features.setuppattern.components.SetupPatternHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.*


@Composable
fun SetupPatternScreen(
    viewModel: SetupPatternViewModel = koinViewModel()
) {
    val snackBarHost = LocalSnackBarHostController.current
    val backStack = LocalBackStack.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            SetupPatternEffect.NavigateBack -> backStack.navigateBack()
            SetupPatternEffect.NavigateToInitBiometric -> backStack.navigateToInitBiometricWithReplaceAll()
            is SetupPatternEffect.ShowSnackBar -> snackBarHost.showSnackBar(it.snackBarData)
        }
    }

    SetupPatternScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SetupPatternScreenContent(
    state: SetupPatternState,
    onIntent: (SetupPatternIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.create_your_pin),
                onLeadingClick = { onIntent(SetupPatternIntent.OnBackClicked) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        onIntent(SetupPatternIntent.OnPatternChanged(emptyList()))
                    }
                }
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SetupPatternHeader()

            PatternView(
                pattern = state.pattern,
                onPatternChanged = { onIntent(SetupPatternIntent.OnPatternChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(
                    text = stringResource(Res.string.confirm),
                    onClick = { onIntent(SetupPatternIntent.OnConfirmClicked) },
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

        SetupPatternScreenContent(
            state = SetupPatternState(),
            onIntent = {}
        )

    }
}
