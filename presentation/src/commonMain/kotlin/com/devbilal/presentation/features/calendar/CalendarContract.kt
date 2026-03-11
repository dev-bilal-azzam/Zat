package com.devbilal.presentation.features.calendar

import com.devbilal.presentation.base.*

data class CalendarState(
    val temp: String? = null
) : UiState

sealed interface CalendarIntent : UiIntent {
    data object OnBackClicked : CalendarIntent
}

sealed interface CalendarEffect : UiEffect {
    data object NavigateBack : CalendarEffect
}
