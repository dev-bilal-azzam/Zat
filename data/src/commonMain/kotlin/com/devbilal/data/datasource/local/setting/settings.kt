package com.devbilal.data.datasource.local.setting

import com.devbilal.domain.util.AppTheme
import com.russhwolf.settings.Settings
/*


internal var Settings.primaryAuthenticationMethod: String
    get() = getString(PRIMARY_AUTHENTICATION_METHOD, "")
    set(value) = putString(PRIMARY_AUTHENTICATION_METHOD, value)


internal var Settings.biometricAuthenticationMethod: String
    get() = getString(BIOMETRIC_AUTHENTICATION_METHODS, "")
    set(value) = putString(BIOMETRIC_AUTHENTICATION_METHODS, value)

*/

internal var Settings.appLanguage: String
    get() = getString(APP_LANGUAGE, "en")
    set(value) = putString(APP_LANGUAGE, value)

internal var Settings.appTheme: String
    get() = getString(APP_THEME, AppTheme.SYSTEM.name)
    set(value) = putString(APP_THEME, value)


const val APP_LANGUAGE = "app_language"
const val APP_THEME = "app_theme"

const val PRIMARY_AUTHENTICATION_METHOD = "primary_authentication_method"

const val BIOMETRIC_AUTHENTICATION_METHODS = "biometric_authentication_methods"