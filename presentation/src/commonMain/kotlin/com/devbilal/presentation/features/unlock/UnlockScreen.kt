package com.devbilal.presentation.features.unlock

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import com.devbilal.domain.model.PrimaryAuthenticationMethod
import com.devbilal.presentation.base.*
import com.devbilal.presentation.common.components.*
import com.devbilal.presentation.common.navigation.*
import com.devbilal.presentation.common.components.PatternView
import com.devbilal.presentation.common.components.PinIndicator
import com.devbilal.presentation.features.unlock.components.UnlockHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UnlockScreen(
    viewModel: UnlockViewModel = koinViewModel()
) {
    val snackBarHost = LocalSnackBarHostController.current
    val backStack = LocalBackStack.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            UnlockEffect.NavigateToHome -> backStack.navigateToHomeWithReplaceAll()
            is UnlockEffect.ShowSnackBar -> snackBarHost.showSnackBar(it.snackBarData)
        }
    }

    UnlockScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun UnlockScreenContent(
    state: UnlockState,
    onIntent: (UnlockIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        onIntent(UnlockIntent.OnPatternChanged(emptyList()))
                    }
                }
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            UnlockHeader(primaryMethod = state.primaryMethod)

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (state.primaryMethod) {
                    is PrimaryAuthenticationMethod.Pin -> {
                        PinIndicator(pin = state.pin)
                    }
                    is PrimaryAuthenticationMethod.Pattern -> {
                        PatternView(
                            pattern = state.pattern,
                            onPatternChanged = { onIntent(UnlockIntent.OnPatternChanged(it)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else -> {}
                }
            }

            if (state.primaryMethod is PrimaryAuthenticationMethod.Pin) {
                Numpad(
                    onNumberClick = { onIntent(UnlockIntent.OnNumberClicked(it)) },
                    onBackspaceClick = { onIntent(UnlockIntent.OnBackspaceClicked) },
                    isBiometricVisible = state.isBiometricEnabled,
                    onBiometricClick = { onIntent(UnlockIntent.OnBiometricClicked) }
                )
            }
        }
    }
}

@Composable
@Preview
fun UnlockPreview() {
    ZatTheme(appTheme = AppTheme.DARK.name) {
        UnlockScreenContent(
            state = UnlockState(
                primaryMethod = PrimaryAuthenticationMethod.Pin(""),
                isBiometricEnabled = true
            ),
            onIntent = {}
        )
    }
}
