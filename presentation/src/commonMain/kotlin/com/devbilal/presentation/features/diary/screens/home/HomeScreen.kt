@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devbilal.designsystem.component.button.FabButton
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.diary.common.navigation.navigateToAddEditDiary
import com.devbilal.presentation.features.diary.screens.home.components.DiaryEntryItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.ic_add
import zat.presentation.generated.resources.no_entries_yet
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state = viewModel.collectState()
    val navigator = LocalNavigator.current

    viewModel.ObserveEffects { effect ->
        when (effect) {
            HomeEffect.NavigateBack -> navigator.navigateBack()
            HomeEffect.NavigateToAddEntry -> navigator.navigateToAddEditDiary()
            is HomeEffect.NavigateToEditEntry -> navigator.navigateToAddEditDiary(effect.id.toString())
        }
    }

    HomeScreenContent(
        state = state,
        onIntent = viewModel::handleIntent
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Theme.colorScheme.background.surfaceLow,
        fullScreen = true,
        fabButton = {
            FabButton(
                painter = painterResource(Res.drawable.ic_add),
                onClick = { onIntent(HomeIntent.OnAddEntryClicked) },
                modifier = Modifier.padding(16.dp)
            )
        }
    ) {
        if (state.entries.isEmpty() && !state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_entries_yet),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.shadeTertiary,
                    modifier = Modifier.padding(32.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.entries) { entry ->
                    DiaryEntryItem(
                        entry = entry,
                        onClick = { onIntent(HomeIntent.OnEntryClicked(entry.id)) }
                    )
                }
            }
        }
    }
}
