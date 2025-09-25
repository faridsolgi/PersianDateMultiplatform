package io.github.faridsolgi.persiandatetime.converter

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toPersianDateTime(): PersianDateTime {
    val date = PersianDateConverter.toPersianDate(this.date) // your extension
    return date.copy(hour = hour, minute = minute, second = second)
}