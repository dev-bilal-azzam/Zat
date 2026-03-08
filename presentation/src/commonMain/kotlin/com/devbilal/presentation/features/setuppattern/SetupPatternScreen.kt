package com.devbilal.presentation.features.setuppattern

import com.devbilal.presentation.base.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import com.devbilal.designsystem.component.scaffold.Scaffold
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SetupPatternScreen(
    viewModel: SetupPatternViewModel = koinViewModel()
) {
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            // handle other screen effects here
            else -> TODO()
        }
    }

    SetupPatternScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SetupPatternScreenContent(
    state: SetupPatternState,
    onIntent: (SetupPatternIntent) -> Unit,
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

        SetupPatternScreen()

    }
}