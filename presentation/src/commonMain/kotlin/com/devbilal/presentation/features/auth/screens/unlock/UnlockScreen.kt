package com.devbilal.presentation.features.auth.screens.unlock

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.components.BiometricButton
import com.devbilal.presentation.common.components.Numpad
import com.devbilal.presentation.common.components.PatternView
import com.devbilal.presentation.common.components.PinIndicator
import com.devbilal.presentation.features.auth.screens.unlock.components.UnlockHeader
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.biometric_auth_description
import zat.presentation.generated.resources.biometric_auth_title
import zat.presentation.generated.resources.cancel

@Composable
fun UnlockScreen(
    viewModel: UnlockViewModel = koinViewModel(),
    title: String? = null,
    description: String? = null,
    onSuccessfulUnlock: () -> Unit
) {
    val snackBarHost = LocalSnackBarHostController.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            UnlockEffect.SuccessfulUnlock -> { onSuccessfulUnlock() }
            is UnlockEffect.ShowSnackBar -> snackBarHost.showSnackBar(it.snackBarData)
        }
    }

    UnlockScreenContent(
        title = title,
        description = description,
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun UnlockScreenContent(
    title: String?,
    description: String?,
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
            UnlockHeader(
                title = title,
                description = description,
                authenticationMethod = state.authenticationMethod
            )

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
