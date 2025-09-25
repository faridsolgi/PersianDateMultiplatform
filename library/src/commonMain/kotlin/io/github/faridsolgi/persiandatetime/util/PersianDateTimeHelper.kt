package io.github.faridsolgi.persiandatetime.util


import io.github.faridsolgi.persiandatetime.converter.toPersianDateTime
import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object PersianDateTimeHelper {

    @OptIn(ExperimentalTime::class)
    fun nowUtc(): PersianDateTime = Clock.System.now().toPersianDateTime(TimeZone.UTC)

    @OptIn(ExperimentalTime::class)
    fun now(timeZone: TimeZone): PersianDateTime = Clock.System.now().toPersianDateTime(timeZone)


}