package com.devbilal.domain.di

import com.devbilal.domain.usecase.authentication.AuthenticateWithPrimaryMethodUseCase
import com.devbilal.domain.usecase.authentication.BiometricAuthenticationUseCase
import com.devbilal.domain.usecase.authentication.GetAuthenticationSettingsUseCase
import com.devbilal.domain.usecase.authentication.IsAvailableBiometricAuthentication
import com.devbilal.domain.usecase.authentication.SetAuthenticationMethodUseCase
import com.devbilal.domain.usecase.settings.AppLanguageUseCase
import com.devbilal.domain.usecase.settings.AppThemeUseCase
import com.devbilal.domain.usecase.settings.OnboardingDoneUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetAuthenticationSettingsUseCase)
    singleOf(::BiometricAuthenticationUseCase)
    singleOf(::AuthenticateWithPrimaryMethodUseCase)
    singleOf(::IsAvailableBiometricAuthentication)
    singleOf(::SetAuthenticationMethodUseCase)

    singleOf(::OnboardingDoneUseCase)

    singleOf(::AppThemeUseCase)
    singleOf(::AppLanguageUseCase)
}