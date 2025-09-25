package io.github.faridsolgi.persiandatetime.converter

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun Clock.nowPersianDate(timeZone: TimeZone): PersianDateTime =
    this.now().toPersianDateTime(timeZone)

@OptIn(ExperimentalTime::class)
val Clock.nowInTehran: PersianDateTime
    get() = this.nowPersianDate(TimeZone.of("Asia/Tehran"))
