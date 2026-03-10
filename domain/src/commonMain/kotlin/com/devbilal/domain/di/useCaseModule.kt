package com.devbilal.domain.di

import com.devbilal.domain.usecase.authentication.*
import com.devbilal.domain.usecase.settings.*
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