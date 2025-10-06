package io.github.faridsolgi.persiandatetime.domain

import kotlinx.datetime.LocalDateTime

internal data class PersianMonthDay(
    val persianDate: PersianDateTime,
    val weekday: PersianWeekday,
    val gregorianDate: LocalDateTime,
)