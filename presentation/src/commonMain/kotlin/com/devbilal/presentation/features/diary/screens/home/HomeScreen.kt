@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.devbilal.designsystem.component.SwipeToDelete
import com.devbilal.designsystem.component.button.FabButton
import com.devbilal.designsystem.component.dialog.ConfirmationDialog
import com.devbilal.designsystem.component.icon.Icon
import com.devbilal.designsystem.component.scaffold.Scaffold
import com.devbilal.designsystem.component.text.Text
import com.devbilal.designsystem.theme.theme.Theme
import com.devbilal.presentation.base.ObserveEffects
import com.devbilal.presentation.base.collectState
import com.devbilal.presentation.common.navigation.LocalNavigator
import com.devbilal.presentation.features.diary.common.navigation.navigateToAddEditDiary
import com.devbilal.presentation.features.diary.common.navigation.navigateToSearch
import com.devbilal.presentation.features.diary.common.navigation.navigateToTrash
import com.devbilal.presentation.features.diary.screens.home.components.HomeEmptyState
import com.devbilal.presentation.features.diary.screens.home.components.HomeTopHeader
import com.devbilal.presentation.features.diary.screens.home.components.RecentEntryCard
import com.devbilal.presentation.features.diary.screens.home.components.TodayEntryCard
import com.devbilal.presentation.features.diary.screens.home.components.TodayPromptCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.cancel
import zat.presentation.generated.resources.delete
import zat.presentation.generated.resources.delete_diary_message
import zat.presentation.generated.resources.delete_diary_title
import zat.presentation.generated.resources.ic_add
import zat.presentation.generated.resources.ic_delete
import zat.presentation.generated.resources.recent_entries_title
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state = viewModel.collectState()
    val navigator = LocalNavigator.current

    viewModel.ObserveEffects { effect ->
        when (effect) {
            HomeEffect.NavigateToAddEntry -> navigator.navigateToAddEditDiary()
            is HomeEffect.NavigateToEditEntry -> navigator.navigateToAddEditDiary(effect.id.toString())
            HomeEffect.NavigateToSearch -> navigator.navigateToSearch()
            HomeEffect.NavigateToTrash -> navigator.navigateToTrash()
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
        topBar = {
            HomeTopHeader(
                greetingRes = state.greetingRes,
                date = state.date,
                streakCount = state.streakCount,
                onSearchClick = { onIntent(HomeIntent.OnSearchClicked) },
                onTrashClick = { onIntent(HomeIntent.OnTrashClicked) }
            )
        },
        fabButton = {
            FabButton(
                painter = painterResource(Res.drawable.ic_add),
                onClick = { onIntent(HomeIntent.OnAddEntryClicked) },
                modifier = Modifier.padding(Theme.spacing._16)
            )
        }
    ) {


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Theme.spacing._16),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing._16)
            ) {
                item {
                    if (state.todayEntry != null) {
                        TodayEntryCard(
                            entry = state.todayEntry,
                            onClick = { onIntent(HomeIntent.OnEntryClicked(state.todayEntry.id)) }
                        )
                    } else {
                        TodayPromptCard(
                            onClick = { onIntent(HomeIntent.OnCreateTodayEntryClicked) }
                        )
                    }
                }

                if (state.entries.isEmpty() && state.todayEntry == null && !state.isLoading) {
                    item {
                        HomeEmptyState()
                    }
                }

                if (state.entries.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(Res.string.recent_entries_title),
                            style = Theme.typography.title.small,
                            color = Theme.colorScheme.shadeSecondary,
                            modifier = Modifier.padding(top = Theme.spacing._8)
                        )
                    }

                    items(state.entries, key = { it.id.toString() }) { entry ->
                        SwipeToDelete(
                            onDelete = { onIntent(HomeIntent.OnSwipeToDelete(entry.id)) },
                            actionContent = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_delete),
                                    tint = Color.White,
                                    modifier = Modifier.size(Theme.spacing._24)
                                )
                            }
                        ) {
                            RecentEntryCard(
                                entry = entry,
                                onClick = { onIntent(HomeIntent.OnEntryClicked(entry.id)) }
                            )
                        }
                    }
                }
            }

    }

    state.pendingDeleteEntryId?.let { _ ->
        ConfirmationDialog(
            title = stringResource(Res.string.delete_diary_title),
            message = stringResource(Res.string.delete_diary_message),
            confirmText = stringResource(Res.string.delete),
            cancelText = stringResource(Res.string.cancel),
            onConfirm = { onIntent(HomeIntent.OnConfirmDelete) },
            onCancel = { onIntent(HomeIntent.OnCancelDelete) }
        )
    }
}
