package com.devbilal.presentation.common.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char


fun LocalDate.formatDisplay(): String {
    val format = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        char(',')
        char(' ')
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        day()
        char(',')
        char(' ')
        year()
    }
    return this.format(format)
}

fun LocalDateTime.formatDateTime(): String {
    val hourStr = hour.toString().padStart(2, '0')
    val minuteStr = minute.toString().padStart(2, '0')
    return "${date.day}/${date.month}/${date.year} $hourStr:$minuteStr"
}
