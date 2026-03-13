package com.devbilal.presentation.features.diary.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.snackbar.LocalSnackBarHostController
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.designsystem.theme.theme.ZatTheme
import com.devbilal.designsystem.util.AppLanguage
import com.devbilal.designsystem.util.AppTheme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.features.diary.common.navigation.LocalNavigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.search


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
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

    SearchScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun SearchScreenContent(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.search),
                style = Theme.typography.headline.large,
                color = Theme.colorScheme.shadePrimary
            )
        }
    }
}


@Composable
@Preview
fun SearchPreview() {

    var language by remember { mutableStateOf(AppLanguage.English) }
    var theme by remember { mutableStateOf(AppTheme.DARK) }

    ZatTheme(
        language = language.iso,
        appTheme = theme.name
    ) {

        SearchScreen()

    }
}