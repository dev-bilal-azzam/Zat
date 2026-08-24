package com.devbilal.presentation.features.diary.screens.trash

import com.devbilal.presentation.base.*
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import com.devbilal.designsystem.component.scaffold.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.presentation.common.navigation.LocalNavigator


@Composable
fun TrashScreen(
    viewModel: TrashViewModel = koinViewModel()
) {
    val snackBarHost = LocalSnackBarHostController.current
    val navigator = LocalNavigator.current
    val state = viewModel.collectState()

    viewModel.ObserveEffects {
        when (it) {
            // handle other screen effects here
            else -> TODO()
        }
    }

    TrashScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun TrashScreenContent(
    state: TrashState,
    onIntent: (TrashIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {

    }
}


@Composable
@Preview
fun TrashPreview() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        TrashScreen()

    }
}