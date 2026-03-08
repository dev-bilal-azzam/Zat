package com.devbilal.zat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.domain.service.AppThemeService
import com.devbilal.domain.service.LocalizationService
import com.devbilal.presentation.common.navigation.NavigationRoot
import org.koin.compose.koinInject

@Composable
@Preview
fun ZatRoot() {

    val localizationService = koinInject<LocalizationService>()
    val appThemeService = koinInject<AppThemeService>()

    val language by localizationService.observeLanguage().collectAsStateWithLifecycle()
    val theme by appThemeService.observeAppTheme().collectAsStateWithLifecycle()


    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        NavigationRoot(modifier = Modifier.fillMaxSize())
    }
}