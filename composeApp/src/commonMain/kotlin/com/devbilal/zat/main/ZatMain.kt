package com.devbilal.zat.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.domain.usecase.settings.AppLanguageUseCase
import com.devbilal.domain.usecase.settings.AppThemeUseCase
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.ZatApp
import com.devbilal.presentation.features.auth.ZatAuth
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import zat.composeapp.generated.resources.Res
import zat.composeapp.generated.resources.zat
import zat.composeapp.generated.resources.zat_logo_with_name

@Composable
@Preview
fun ZatMain(
    viewModel: MainViewModel = koinViewModel()
) {

    val languageUseCase = koinInject<AppLanguageUseCase>()
    val themeUseCase = koinInject<AppThemeUseCase>()

    val language by languageUseCase.observeAppLanguage().collectAsStateWithLifecycle()
    val theme by themeUseCase.observeAppTheme().collectAsStateWithLifecycle()

    val state = viewModel.collectState()

    LaunchedEffect(Unit) {
        println("Track : ZatMain")
    }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {
        when (state.currentDestination) {
            ZatDestination.Home -> ZatApp()
            ZatDestination.Auth -> ZatAuth(
                isOnboardingDone = state.isOnboardingDone ?: false,
                navigateHome = { viewModel.handleIntent(MainIntent.NavigateHome) }
            )
            ZatDestination.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colorScheme.background.surfaceLow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.zat_logo_with_name),
                    contentDescription = stringResource(Res.string.zat),
                    modifier = Modifier.size(160.dp)
                )
            }
        }
    }
}
