@file:OptIn(ExperimentalUuidApi::class)

package com.devbilal.presentation.features.diary.screens.home

import com.devbilal.domain.entity.DiaryEntrySummary
import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class HomeState(
    val entries: List<DiaryEntrySummary> = emptyList(),
    val isLoading: Boolean = false
) : UiState

sealed interface HomeIntent : UiIntent {
    data object OnBackClicked : HomeIntent
    data class OnEntryClicked(val id: Uuid) : HomeIntent
    data object OnAddEntryClicked : HomeIntent
}

sealed interface HomeEffect : UiEffect {
    data object NavigateBack : HomeEffect
    data class NavigateToEditEntry(val id: Uuid) : HomeEffect
    data object NavigateToAddEntry : HomeEffect
}
