package com.devbilal.presentation.features.diary.screens.security

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.appBar.AppBar
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.auth.common.navigation.navigateToSetupPattern
import com.devbilal.presentation.features.auth.common.navigation.navigateToSetupPin
import com.devbilal.presentation.features.diary.common.navigation.navigateToSecurity
import com.devbilal.presentation.features.diary.common.navigation.navigateToUnlock
import com.devbilal.presentation.features.diary.screens.security.components.AuthenticationMethodsSection
import com.devbilal.presentation.features.diary.screens.security.components.BiometricSection
import com.devbilal.presentation.features.diary.screens.security.components.ProtectionToggle
import com.devbilal.presentation.features.diary.screens.security.components.SecurityHeader
import com.devbilal.presentation.features.diary.screens.security.components.SecurityNotice
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.security

@Composable
fun SecurityScreen(
    viewModel: SecurityViewModel = koinViewModel()
) {
    val snackBarHost = LocalSnackBarHostController.current
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            SecurityEffect.NavigateBack -> navigator.navigateBack()

            SecurityEffect.NavigateToUnlockForSetPin -> {
                navigator.navigateToUnlock {
                    navigator.navigateToSetupPin {
                        navigator.navigateToSecurity()
                        viewModel.handleIntent(SecurityIntent.OnSuccessfulSetup)
                    }
                }
            }

            SecurityEffect.NavigateToUnlockForSetPattern -> {
                navigator.navigateToUnlock {
                    navigator.navigateToSetupPattern {
                        navigator.navigateToSecurity()
                        viewModel.handleIntent(SecurityIntent.OnSuccessfulSetup)
                    }
                }
            }

            SecurityEffect.NavigateToSetupPin -> {
                navigator.navigateToSetupPin {
                    navigator.navigateToSecurity()
                    viewModel.handleIntent(SecurityIntent.OnSuccessfulSetup)
                }
            }

            SecurityEffect.NavigateToSetupPattern -> {
                navigator.navigateToSetupPattern {
                    navigator.navigateToSecurity()
                    viewModel.handleIntent(SecurityIntent.OnSuccessfulSetup)
                }
            }

            is SecurityEffect.ShowSnackBar -> snackBarHost.showSnackBar(it.snackBarData)
        }
    }

    SecurityScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SecurityScreenContent(
    state: SecurityState,
    onIntent: (SecurityIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        topBar = {
            AppBar(
                title = stringResource(Res.string.security),
                onLeadingClick = { onIntent(SecurityIntent.OnBackClicked) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            SecurityHeader()

            ProtectionToggle(
                isEnabled = state.isProtectionEnabled,
                onToggle = { onIntent(SecurityIntent.OnToggleProtection(it)) }
            )

            if (state.isProtectionEnabled) {
                AuthenticationMethodsSection(
                    selectedMethod = state.selectedMethod,
                    onMethodSelected = { onIntent(SecurityIntent.OnMethodSelected(it)) },
                    onChangePin = { onIntent(SecurityIntent.OnChangePinClicked) },
                    onChangePattern = { onIntent(SecurityIntent.OnChangePatternClicked) }
                )

                BiometricSection(
                    isEnabled = state.isBiometricEnabled,
                    onToggle = { onIntent(SecurityIntent.OnToggleBiometric(it)) }
                )
            }

            SecurityNotice()
        }
    }
}


@Composable
@Preview
fun SecurityPreview() {
    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        SecurityScreenContent(
            state = SecurityState(
                isProtectionEnabled = true,
                selectedMethod = AuthenticationMethod.Pin(null),
                isBiometricEnabled = false,
            ),
            onIntent = {}
        )
    }
}
