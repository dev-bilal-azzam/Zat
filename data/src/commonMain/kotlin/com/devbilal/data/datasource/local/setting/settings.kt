package com.devbilal.data.datasource.local.setting

import com.devbilal.domain.model.BiometricAuthenticationMethod
import com.devbilal.domain.util.AppLanguage
import com.devbilal.domain.util.AppTheme


internal var SettingsStorage.primaryAuthenticationMethod: PrimaryAuthenticationMethodDto
    get() = getSerializable(
        PRIMARY_AUTHENTICATION_METHOD,
        PrimaryAuthenticationMethodDto.None,
        PrimaryAuthenticationMethodDto.serializer()
    )
    set(method) = putSerializable(PRIMARY_AUTHENTICATION_METHOD, method, PrimaryAuthenticationMethodDto.serializer())


internal var SettingsStorage.biometricAuthenticationMethod: Set<BiometricAuthenticationMethod>
    get() = getEnumSet(BIOMETRIC_AUTHENTICATION_METHODS, emptySet())
    set(set) = putEnumSet(BIOMETRIC_AUTHENTICATION_METHODS, set)

internal var SettingsStorage.credential: String?
    get() = getStringOrNull(CREDENTIAL)
    set(value) = putStringOrNull(CREDENTIAL, value)

internal var SettingsStorage.appLanguage: AppLanguage
    get() = AppLanguage.valueOf(getString(APP_LANGUAGE, AppLanguage.ENGLISH.name))
    set(language) = putString(APP_LANGUAGE, language.name)

internal var SettingsStorage.appTheme: AppTheme
    get() = AppTheme.valueOf(getString(APP_THEME, AppTheme.SYSTEM.name))
    set(theme) = putString(APP_THEME, theme.name)


const val APP_LANGUAGE = "app_language"
const val APP_THEME = "app_theme"

const val PRIMARY_AUTHENTICATION_METHOD = "primary_authentication_method"
const val BIOMETRIC_AUTHENTICATION_METHODS = "biometric_authentication_methods"
const val CREDENTIAL = "credential"