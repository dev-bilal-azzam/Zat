package com.devbilal.domain.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"

fun LocalDateTime.Companion.now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.today() = LocalDateTime.now().date

fun LocalTime.now() = LocalDateTime.now().time