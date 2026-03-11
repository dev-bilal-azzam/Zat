package com.devbilal.designsystem.theme.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.component.snackbar.SnackBarHostController
import com.devbilal.designsystem.theme.color.scheme.ColorScheme
import com.devbilal.designsystem.theme.color.scheme.DarkColorScheme
import com.devbilal.designsystem.theme.color.scheme.LightColorScheme
import com.devbilal.designsystem.theme.color.scheme.LocalColorScheme
import com.devbilal.designsystem.theme.radius.LocalRadius
import com.devbilal.designsystem.theme.radius.Radius
import com.devbilal.designsystem.theme.radius.ZatRadius
import com.devbilal.designsystem.theme.spacing.LocalSpacing
import com.devbilal.designsystem.theme.spacing.Spacing
import com.devbilal.designsystem.theme.spacing.ZatSpacing
import com.devbilal.designsystem.theme.typography.LocalTypography
import com.devbilal.designsystem.theme.typography.Typography
import com.devbilal.designsystem.theme.typography.createThemeTypography
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
@Composable
fun ZatTheme(
    language: String = AppLanguage.English.iso,
    appTheme: String = AppTheme.SYSTEM.name,
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val isFollowingSystemDarkMode = remember(appTheme, isSystemInDarkTheme) {
        appTheme == AppTheme.SYSTEM.name && isSystemInDarkTheme
    }

    val colorScheme = remember(appTheme, isFollowingSystemDarkMode) {
        when (appTheme) {
            AppTheme.LIGHT.name -> LightColorScheme
            AppTheme.DARK.name -> DarkColorScheme
            AppTheme.SYSTEM.name -> if (isSystemInDarkTheme) DarkColorScheme else LightColorScheme
            else -> LightColorScheme
        }
    }
    val typography = createThemeTypography(language)
    val layoutDirection = remember(language) {
        if (language == AppLanguage.Arabic.iso) LayoutDirection.Rtl else LayoutDirection.Ltr
    }

    val snackBarHostController = remember { SnackBarHostController() }

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalSpacing provides ZatSpacing,
        LocalRadius provides ZatRadius,
        LocalTypography provides typography,
        LocalLayoutDirection provides layoutDirection,
        LocalSnackBarHostController provides snackBarHostController
    ) {
        content()
    }
}

object Theme {
    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current

    val typography: Typography
        @Composable @ReadOnlyComposable get() = LocalTypography.current

    val spacing: Spacing
        @Composable @ReadOnlyComposable get() = LocalSpacing.current

    val radius: Radius
        @Composable @ReadOnlyComposable get() = LocalRadius.current
}