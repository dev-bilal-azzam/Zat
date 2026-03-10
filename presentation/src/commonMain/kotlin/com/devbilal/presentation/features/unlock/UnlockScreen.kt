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
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.*
import com.devbilal.presentation.common.components.*
import com.devbilal.presentation.common.navigation.*
import com.devbilal.presentation.common.components.PatternView
import com.devbilal.presentation.common.components.PinIndicator
import com.devbilal.presentation.features.unlock.components.BiometricButton
import com.devbilal.presentation.features.unlock.components.UnlockHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.*

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
            UnlockHeader(primaryMethod = state.authenticationMethod)

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {

                val biometricTitle = stringResource(Res.string.biometric_auth_title)
                val biometricDescription = stringResource(Res.string.biometric_auth_description)
                val biometricCancel = stringResource(Res.string.cancel)

                if (state.authenticationMethod is AuthenticationMethod.Pin) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PinIndicator(pin = state.pin)

                        Numpad(
                            onNumberClick = { onIntent(UnlockIntent.OnNumberClicked(it)) },
                            onBackspaceClick = { onIntent(UnlockIntent.OnBackspaceClicked) },
                            isBiometricVisible = state.isBiometricEnabled,
                            onBiometricClick = {
                                onIntent(
                                    UnlockIntent.OnBiometricClicked(
                                        biometricTitle,
                                        biometricDescription,
                                        biometricCancel
                                    )
                                )
                            }
                        )
                    }
                }

                if (state.authenticationMethod is AuthenticationMethod.Pattern) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PatternView(
                            pattern = state.pattern,
                            onPatternChanged = { onIntent(UnlockIntent.OnPatternChanged(it)) },
                            onPatternCompleted = { onIntent(UnlockIntent.OnPatternCompleted) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (state.isBiometricEnabled) {
                            BiometricButton(
                                onClick = {
                                    onIntent(
                                        UnlockIntent.OnBiometricClicked(
                                            biometricTitle,
                                            biometricDescription,
                                            biometricCancel
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
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
                authenticationMethod = AuthenticationMethod.Pin(""),
                isBiometricEnabled = true
            ),
            onIntent = {}
        )
    }
}

