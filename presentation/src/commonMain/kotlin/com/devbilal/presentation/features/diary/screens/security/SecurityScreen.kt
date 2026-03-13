package com.devbilal.presentation.features.diary.screens.security

import com.devbilal.presentation.base.*
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import com.devbilal.designsystem.component.scaffold.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.presentation.features.diary.common.navigation.LocalNavigator


@Composable
fun SecurityScreen(
    viewModel: SecurityViewModel = koinViewModel()
) {
    val snackBarHost = LocalSnackBarHostController.current
    val backState = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            // handle other screen effects here
            else -> TODO()
        }
    }

    SecurityScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SecurityScreenContent(
    state: SecurityState,
    onIntent: (SecurityIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {

    }
}


@Composable
@Preview
fun SecurityPreview() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        SecurityScreen()

    }
}