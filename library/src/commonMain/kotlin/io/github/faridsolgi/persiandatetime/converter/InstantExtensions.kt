package io.github.faridsolgi.persiandatetime.converter

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.util.Main
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
        /**
         * Converts this [Instant] to a [PersianDateTime] in the given [timeZone].
         *
         * Usage:
         * ```kotlin
         * val nowInstant = Clock.System.now()
         * val persianDate = nowInstant.toPersianDateTime(TimeZone.of("Asia/Tehran"))
         * println(persianDate.toDateTimeString())
         * ```
         *
         * @param timeZone The time zone to use for conversion.
         * @return The corresponding PersianDateTime.
         */
fun Instant.toPersianDateTime(timeZone: TimeZone): PersianDateTime =
    Main.persianDateConverter.fromGregorian(this.toLocalDateTime(timeZone).date)
