package com.devbilal.presentation.features.addeditdiary

import com.devbilal.presentation.base.*
import com.devbilal.presentation.common.navigation.*
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.theme.theme.*
import com.devbilal.designsystem.util.*
import com.devbilal.designsystem.component.scaffold.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AddEditDiaryScreen(
    viewModel: AddEditDiaryViewModel = koinViewModel()
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

    AddEditDiaryScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun AddEditDiaryScreenContent(
    state: AddEditDiaryState,
    onIntent: (AddEditDiaryIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {

    }
}


@Composable
@Preview
fun AddEditDiaryPreview() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        AddEditDiaryScreen()

    }
}