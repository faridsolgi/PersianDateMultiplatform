package io.github.faridsolgi.persiandatetime.converter

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
        /**
         * Returns the current time as a PersianDateTime in the given [timeZone].
         *
         * Usage:
         * ```kotlin
         * val clock = Clock.System
         * val persianNow = clock.nowPersianDate(TimeZone.of("Asia/Tehran"))
         * println(persianNow.toDateTimeString())
         * ```
         */
fun Clock.nowPersianDate(timeZone: TimeZone): PersianDateTime =
    this.now().toPersianDateTime(timeZone)

@OptIn(ExperimentalTime::class)
        /**
         * Returns the current time as a PersianDateTime in the Tehran time zone.
         *
         * Usage:
         * ```kotlin
         * val clock = Clock.System
         * val tehranNow = clock.nowInTehran
         * println(tehranNow.toDateTimeString())
         * ```
         */
val Clock.nowInTehran: PersianDateTime
    get() = this.nowPersianDate(TimeZone.of("Asia/Tehran"))
