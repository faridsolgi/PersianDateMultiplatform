package com.faridsolgi.persiandatemultiplatform.util

import com.faridsolgi.persiandatemultiplatform.converter.toPersianDateTime
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object PersianDateTimeHelper {

    @OptIn(ExperimentalTime::class)
    fun nowUtc(): PersianDateTime = Clock.System.now().toPersianDateTime(TimeZone.UTC)

    @OptIn(ExperimentalTime::class)
    fun now(timeZone: TimeZone): PersianDateTime = Clock.System.now().toPersianDateTime(timeZone)


}