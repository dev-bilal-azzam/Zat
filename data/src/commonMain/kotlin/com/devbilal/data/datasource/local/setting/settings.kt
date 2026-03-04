package com.devbilal.data.datasource.local.setting

import com.devbilal.domain.util.AppTheme
import com.russhwolf.settings.Settings

internal var Settings.appLanguage: String
    get() = getString(APP_LANGUAGE, "en")
    set(value) = putString(APP_LANGUAGE, value)

internal var Settings.appTheme: String
    get() = getString(APP_THEME, AppTheme.SYSTEM.name)
    set(value) = putString(APP_THEME, value)


const val APP_LANGUAGE = "app_language"
const val APP_THEME = "app_theme"