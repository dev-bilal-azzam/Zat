package com.devbilal.presentation.features.diary.screens.calendar

import com.devbilal.presentation.base.BaseViewModel

class CalendarViewModel : BaseViewModel<CalendarState, CalendarIntent, CalendarEffect>(
    CalendarState()
) {

    init {
    }

    override fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            // handle all of your Intents here
            else -> TODO()
        }
    }
}