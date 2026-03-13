package com.devbilal.data.datasource.local.setting

import com.devbilal.domain.util.AppLanguage
import com.devbilal.domain.util.AppTheme


internal var SettingsStorage.authenticationMethod: PrimaryAuthenticationMethodDto
    get() = getSerializable(
        PRIMARY_AUTHENTICATION_METHOD,
        PrimaryAuthenticationMethodDto.None,
        PrimaryAuthenticationMethodDto.serializer()
    )
    set(method) = putSerializable(PRIMARY_AUTHENTICATION_METHOD, method, PrimaryAuthenticationMethodDto.serializer())


internal var SettingsStorage.isBiometricAuthEnabled: Boolean
    get() = getBoolean(IS_BIOMETRIC_AUTH_ENABLED)
    set(value) = putBoolean(IS_BIOMETRIC_AUTH_ENABLED, value)

internal var SettingsStorage.pinCredential: String?
    get() = getStringOrNull(PIN_CREDENTIAL)
    set(value) = putStringOrNull(PIN_CREDENTIAL, value)

internal var SettingsStorage.patternCredential: String?
    get() = getStringOrNull(PATTERN_CREDENTIAL)
    set(value) = putStringOrNull(PATTERN_CREDENTIAL, value)

internal var SettingsStorage.appLanguage: AppLanguage
    get() = AppLanguage.valueOf(getString(APP_LANGUAGE, AppLanguage.ENGLISH.name))
    set(language) = putString(APP_LANGUAGE, language.name)

internal var SettingsStorage.appTheme: AppTheme
    get() = AppTheme.valueOf(getString(APP_THEME, AppTheme.SYSTEM.name))
    set(theme) = putString(APP_THEME, theme.name)



internal var SettingsStorage.isOnboardingDone: Boolean
    get() = getBoolean(IS_ONBOARDING_DONE)
    set(value) = putBoolean(IS_ONBOARDING_DONE, value)

const val APP_LANGUAGE = "app_language"
const val APP_THEME = "app_theme"

const val PRIMARY_AUTHENTICATION_METHOD = "primary_authentication_method"
const val IS_BIOMETRIC_AUTH_ENABLED = "is_biometric_auth_enabled"
const val PIN_CREDENTIAL = "pin_credential"
const val PATTERN_CREDENTIAL = "pattern_credential"

const val IS_ONBOARDING_DONE = "is_onboarding_done"
