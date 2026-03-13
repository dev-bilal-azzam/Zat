package com.devbilal.presentation.features.diary.screens.security

import com.devbilal.designsystem.component.snackbar.SnackBarData
import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.usecase.authentication.BiometricAuthenticationUseCase
import com.devbilal.domain.usecase.authentication.GetAuthenticationSettingsUseCase
import com.devbilal.domain.usecase.authentication.SetAuthenticationMethodUseCase
import com.devbilal.presentation.base.BaseViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.pattern_set_successfully
import zat.presentation.generated.resources.pin_set_successfully

class SecurityViewModel(
    private val getAuthenticationSettingsUseCase: GetAuthenticationSettingsUseCase,
    private val biometricAuthenticationUseCase: BiometricAuthenticationUseCase,
    private val setAuthenticationMethodUseCase: SetAuthenticationMethodUseCase
) : BaseViewModel<SecurityState, SecurityIntent, SecurityEffect>(SecurityState()) {

    init {
        loadAuthenticationSettings()
    }

    override fun handleIntent(intent: SecurityIntent) {
        when (intent) {
            SecurityIntent.OnBackClicked -> sendEffect(SecurityEffect.NavigateBack)
            is SecurityIntent.OnToggleProtection -> toggleProtection(intent.enabled)
            is SecurityIntent.OnMethodSelected -> updateSelectedMethod(intent.method)
            is SecurityIntent.OnToggleBiometric -> toggleBiometric(intent.enabled)
            SecurityIntent.OnChangePinClicked -> sendEffect(SecurityEffect.NavigateToUnlockForChangePin)
            SecurityIntent.OnChangePatternClicked -> sendEffect(SecurityEffect.NavigateToUnlockForChangePattern)
            SecurityIntent.OnSuccessfulSetup -> onSuccessfulSetup()
        }
    }

    private fun loadAuthenticationSettings() {
        safeExecute(
            block = {
                val settings = getAuthenticationSettingsUseCase()
                Pair(settings.method, settings.isBiometricAuthEnabled)
            },
            onSuccess = { (method, isBiometricEnabled) ->
                updateState {
                    copy(
                        isProtectionEnabled = method != AuthenticationMethod.None,
                        selectedMethod = method,
                        isBiometricEnabled = isBiometricEnabled
                    )
                }
            }
        )
    }

    private fun toggleProtection(enabled: Boolean) {
        updateState { copy(isProtectionEnabled = enabled) }

        if (!enabled) {
            safeExecute(
                block = { setAuthenticationMethodUseCase(AuthenticationMethod.None) },
                onSuccess = { loadAuthenticationSettings() }
            )
        }
    }

    private fun updateSelectedMethod(method: AuthenticationMethod) {
        // todo logic corrupted
        updateState { copy(selectedMethod = method) }
        when (method) {
            is AuthenticationMethod.Pin -> sendEffect(SecurityEffect.NavigateToSetupPin)
            is AuthenticationMethod.Pattern -> sendEffect(SecurityEffect.NavigateToSetupPattern)
            else -> {}
        }
    }

    private fun toggleBiometric(enabled: Boolean) {
        safeExecute(
            block = {
                if (enabled) biometricAuthenticationUseCase.enable()
                else biometricAuthenticationUseCase.disable()
            },
            onSuccess = {
                updateState { copy(isBiometricEnabled = enabled) }
            }
        )
    }

    private fun onSuccessfulSetup() {
        loadAuthenticationSettings()
        val messageRes = if (state.value.selectedMethod is AuthenticationMethod.Pin) {
            Res.string.pin_set_successfully
        } else {
            Res.string.pattern_set_successfully
        }
        
        sendEffect(
            SecurityEffect.ShowSnackBar(
                SnackBarData(
                    title = UiText.DynamicString("Success"),
                    message = UiText.StringRes(messageRes),
                    isError = false
                )
            )
        )
    }
}
