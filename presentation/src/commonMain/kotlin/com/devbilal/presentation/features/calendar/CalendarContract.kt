package com.devbilal.presentation.features.calendar

import com.devbilal.presentation.base.UiEffect
import com.devbilal.presentation.base.UiIntent
import com.devbilal.presentation.base.UiState

data class CalendarState(
    val temp: String? = null
) : UiState

sealed interface CalendarIntent : UiIntent {
    data object OnBackClicked : CalendarIntent
}

sealed interface CalendarEffect : UiEffect {
    data object NavigateBack : CalendarEffect
}
