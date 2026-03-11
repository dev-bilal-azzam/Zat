package com.devbilal.zat.main

import com.devbilal.domain.model.AuthenticationMethod
import com.devbilal.domain.model.AuthenticationSettings
import com.devbilal.domain.usecase.authentication.GetAuthenticationSettingsUseCase
import com.devbilal.domain.usecase.settings.OnboardingDoneUseCase
import com.devbilal.presentation.base.BaseViewModel

class MainViewModel(
    private val onboardingDoneUseCase: OnboardingDoneUseCase,
    private val getAuthenticationSettingsUseCase: GetAuthenticationSettingsUseCase
) : BaseViewModel<MainState, MainIntent, MainEffect>(MainState()) {

    init {
        loadIsOnboarding()
    }

    private fun loadIsOnboarding() {
        safeExecute(
            block = onboardingDoneUseCase::isOnboardingDone,
            onSuccess = ::onSuccessLoadOnboardingDone
        )
    }

    private fun onSuccessLoadOnboardingDone(isDone: Boolean) {
        if (isDone) {
            getAuthenticationSettings()
        } else {
            updateState {
                copy(
                    isOnboardingDone = false,
                    currentDestination = ZatDestination.Auth
                )
            }
        }
    }

    private fun getAuthenticationSettings() {
        safeExecute(
            block = getAuthenticationSettingsUseCase::invoke,
            onSuccess = ::onSuccessGetAuthenticationSettings
        )
    }

    private fun onSuccessGetAuthenticationSettings(settings: AuthenticationSettings) {
        updateState {
            copy(
                isOnboardingDone = true,
                authenticationMethod = settings.method,
                currentDestination = if (settings.method is AuthenticationMethod.None)
                    ZatDestination.Home else ZatDestination.Auth
            )
        }
    }

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.NavigateHome -> updateState { copy(currentDestination = ZatDestination.Home) }
        }
    }
}
