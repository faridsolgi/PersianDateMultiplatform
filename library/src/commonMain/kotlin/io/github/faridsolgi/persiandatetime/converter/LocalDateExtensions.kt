package io.github.faridsolgi.persiandatetime.converter

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.LocalDate

fun LocalDate.toPersianDateTime(hour: Int = 0, minute: Int = 0, second: Int = 0): PersianDateTime {
    val date = PersianDateConverter.toPersianDate(this) // your extension
    return date.copy(hour = hour, minute = minute, second = second)
}