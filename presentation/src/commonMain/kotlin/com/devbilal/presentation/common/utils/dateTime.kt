package com.devbilal.presentation.common.utils

import com.devbilal.designsystem.component.uitext.UiText
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.StringResource
import zat.presentation.generated.resources.Res
import zat.presentation.generated.resources.day_friday
import zat.presentation.generated.resources.day_monday
import zat.presentation.generated.resources.day_saturday
import zat.presentation.generated.resources.day_sunday
import zat.presentation.generated.resources.day_thursday
import zat.presentation.generated.resources.day_tuesday
import zat.presentation.generated.resources.day_wednesday
import zat.presentation.generated.resources.month_april
import zat.presentation.generated.resources.month_august
import zat.presentation.generated.resources.month_december
import zat.presentation.generated.resources.month_february
import zat.presentation.generated.resources.month_january
import zat.presentation.generated.resources.month_july
import zat.presentation.generated.resources.month_june
import zat.presentation.generated.resources.month_march
import zat.presentation.generated.resources.month_may
import zat.presentation.generated.resources.month_november
import zat.presentation.generated.resources.month_october
import zat.presentation.generated.resources.month_september
import zat.presentation.generated.resources.today_date_format

fun LocalDateTime.formatDateTime(): String {
    val hourStr = hour.toString().padStart(2, '0')
    val minuteStr = minute.toString().padStart(2, '0')
    return "${date.day}/${date.month}/${date.year} $hourStr:$minuteStr"
}


fun LocalDate.localizedFormat(): UiText {
    val dayOfWeekRes = getDayOfWeekRes(dayOfWeek)
    val monthRes = getMonthRes(month)

    return UiText.StringRes(
        resId = Res.string.today_date_format,
        formatArgs = listOf(
            UiText.StringRes(dayOfWeekRes),
            UiText.StringRes(monthRes),
            day
        ).toTypedArray()
    )
}

private fun getDayOfWeekRes(day: DayOfWeek): StringResource {
    return when (day) {
        DayOfWeek.MONDAY -> Res.string.day_monday
        DayOfWeek.TUESDAY -> Res.string.day_tuesday
        DayOfWeek.WEDNESDAY -> Res.string.day_wednesday
        DayOfWeek.THURSDAY -> Res.string.day_thursday
        DayOfWeek.FRIDAY -> Res.string.day_friday
        DayOfWeek.SATURDAY -> Res.string.day_saturday
        DayOfWeek.SUNDAY -> Res.string.day_sunday
    }
}

private fun getMonthRes(month: Month): StringResource {
    return when (month) {
        Month.JANUARY -> Res.string.month_january
        Month.FEBRUARY -> Res.string.month_february
        Month.MARCH -> Res.string.month_march
        Month.APRIL -> Res.string.month_april
        Month.MAY -> Res.string.month_may
        Month.JUNE -> Res.string.month_june
        Month.JULY -> Res.string.month_july
        Month.AUGUST -> Res.string.month_august
        Month.SEPTEMBER -> Res.string.month_september
        Month.OCTOBER -> Res.string.month_october
        Month.NOVEMBER -> Res.string.month_november
        Month.DECEMBER -> Res.string.month_december
    }
}