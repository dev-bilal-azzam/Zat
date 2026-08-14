@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.home

import com.devbilal.designsystem.component.uitext.UiText
import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState
import org.jetbrains.compose.resources.StringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class HomeState(
    val greetingRes: StringResource? = null,
    val streakCount: Int = 0,
    val todayEntry: DiaryEntrySummary? = null,
    val entries: List<DiaryEntrySummary> = emptyList(),
    val isLoading: Boolean = false,
    val pendingDeleteEntryId: Uuid? = null,
    val date: UiText? = null,
) : UiState

sealed interface HomeIntent : UiIntent {
    data class OnEntryClicked(val id: Uuid) : HomeIntent
    data object OnAddEntryClicked : HomeIntent
    data class OnSwipeToDelete(val id: Uuid) : HomeIntent
    data object OnConfirmDelete : HomeIntent
    data object OnCancelDelete : HomeIntent
    data object OnSearchClicked : HomeIntent
    data object OnTrashClicked : HomeIntent
    data object OnCreateTodayEntryClicked : HomeIntent
}

sealed interface HomeEffect : UiEffect {
    data class NavigateToEditEntry(val id: Uuid) : HomeEffect
    data object NavigateToAddEntry : HomeEffect
    data object NavigateToSearch : HomeEffect
    data object NavigateToTrash : HomeEffect
}
