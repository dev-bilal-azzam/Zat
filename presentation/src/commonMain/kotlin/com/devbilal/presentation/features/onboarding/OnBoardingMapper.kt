package com.devbilal.presentation.features.onboarding

import com.devbilal.designsystem.util.AppTheme
import com.devbilal.domain.util.AppTheme as SettingsAppTheme

fun AppTheme.toSettingsAppTheme(): SettingsAppTheme {
    return when(this) {
        AppTheme.DARK -> SettingsAppTheme.DARK
        AppTheme.LIGHT -> SettingsAppTheme.LIGHT
        AppTheme.SYSTEM -> SettingsAppTheme.SYSTEM
    }
}

fun SettingsAppTheme.toAppTheme(): AppTheme {
    return when(this) {
        SettingsAppTheme.DARK -> AppTheme.DARK
        SettingsAppTheme.LIGHT -> AppTheme.LIGHT
        SettingsAppTheme.SYSTEM -> AppTheme.SYSTEM
    }
}