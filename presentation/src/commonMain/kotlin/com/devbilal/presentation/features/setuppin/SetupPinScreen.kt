package com.devbilal.presentation.features.setuppin

import com.devbilal.presentation.base.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.designsystem.component.scaffold.Scaffold
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SetupPinScreen(
    viewModel: SetupPinViewModel = koinViewModel()
) {
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            // handle other screen effects here
            else -> TODO()
        }
    }

    SetupPinScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SetupPinScreenContent(
    state: SetupPinState,
    onIntent: (SetupPinIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {

    }
}


@Composable
@Preview
fun App() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        SetupPinScreen()

    }
}