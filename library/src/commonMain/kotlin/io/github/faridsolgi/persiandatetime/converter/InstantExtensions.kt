package io.github.faridsolgi.persiandatetime.converter

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun Instant.toPersianDateTime(timeZone: TimeZone): PersianDateTime =
    PersianDateConverter.toPersianDate(this.toLocalDateTime(timeZone).date)
