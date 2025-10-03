package io.github.faridsolgi.persiandatetime.extensions


import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.domain.PersianMonth
import io.github.faridsolgi.persiandatetime.domain.PersianWeekday
import io.github.faridsolgi.persiandatetime.util.Main
import io.github.faridsolgi.persiandatetime.util.PersianDateTimeFormat
import io.github.faridsolgi.persiandatetime.util.PersianLeap
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Converts a PersianDateTime to a LocalDate.
 *
 * Usage:
 * ```kotlin
 * val persianDate = PersianDateTime(1402, 7, 15, 14, 30, 0)
 * val localDate: LocalDate = persianDate.toLocalDate()
 * ```
 */
fun PersianDateTime.toLocalDate(): LocalDate = Main.persianDateConverter.toGregorian(this).date


@OptIn(ExperimentalTime::class)
fun PersianDateTime.toInstant(timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant =
    this.toLocalDateTime().toInstant(timeZone)

@OptIn(ExperimentalTime::class)
fun PersianDateTime.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long =
    this.toLocalDateTime().toInstant(timeZone).toEpochMilliseconds()

/**
 * Converts a PersianDateTime to a LocalDateTime.
 *
 * Usage:
 * ```kotlin
 * val persianDate = PersianDateTime(1402, 7, 15, 14, 30, 0)
 * val localDateTime: LocalDateTime = persianDate.toLocalDateTime()
 * ```
 */
fun PersianDateTime.toLocalDateTime(): LocalDateTime = Main.persianDateConverter.toGregorian(this)

/**
 * Adds [days] to the PersianDateTime.
 *
 * Usage:
 * ```kotlin
 * val newDate = persianDate.plusDays(5)
 * ```
 */
fun PersianDateTime.plusDays(days: Int): PersianDateTime {
    val localDateTime = this.toLocalDateTime()
    val newDate = localDateTime.date.plus(days, DateTimeUnit.DAY)
    return newDate.toPersianDateTime(this.hour, this.minute, this.second)
}

/**
 * Subtracts [days] from the PersianDateTime.
 *
 * Usage:
 * ```kotlin
 * val newDate = persianDate.minusDays(3)
 * ```
 */
fun PersianDateTime.minusDays(days: Int): PersianDateTime = plusDays(-days)

/**
 * Checks if this PersianDateTime is before [other].
 *
 * Usage:
 * ```kotlin
 * val result = persianDate.isBefore(otherDate)
 * ```
 */
fun PersianDateTime.isBefore(other: PersianDateTime) = this.toLocalDateTime() < other.toLocalDateTime()

/**
 * Checks if this PersianDateTime is after [other].
 *
 * Usage:
 * ```kotlin
 * val result = persianDate.isAfter(otherDate)
 * ```
 */
fun PersianDateTime.isAfter(other: PersianDateTime) = this.toLocalDateTime() > other.toLocalDateTime()

/**
 * Checks if this PersianDateTime is between [start] and [end].
 *
 * Usage:
 * ```kotlin
 * val result = persianDate.isBetween(startDate, endDate)
 * ```
 */
fun PersianDateTime.isBetween(start: PersianDateTime, end: PersianDateTime) =
    this.toLocalDateTime() >= start.toLocalDateTime() && this.toLocalDateTime() <= end.toLocalDateTime()

/**
 * Checks if the year of this PersianDateTime is a leap year.
 *
 * Usage:
 * ```kotlin
 * val isLeap = persianDate.isLeapYear()
 * ```
 */
fun PersianDateTime.isLeapYear(): Boolean = PersianLeap.getLeapFactor(year) == 0

/**
 * Returns the number of days in the month of this PersianDateTime.
 *
 * Usage:
 * ```kotlin
 * val length = persianDate.monthLength()
 * ```
 */
fun PersianDateTime.monthLength(): Int = when {
    month < 7 -> 31
    month < 12 -> 30
    month == 12 -> if (this.isLeapYear()) 30 else 29
    else -> 0
}

/**
 * Returns the month of this PersianDateTime.
 *
 * Usage:
 * ```kotlin
 * val name = persianDate.persianMonth()
 * ```
 */
fun PersianDateTime.persianMonth(): PersianMonth = PersianMonth.entries.toTypedArray()
    .getOrElse(month) { PersianMonth.UNKNOWN }

/**
 * Returns the Persian day of the week.
 *
 * Usage:
 * ```kotlin
 * val weekday = persianDate.persianDayOfWeek()
 * ```
 */
fun PersianDateTime.persianDayOfWeek(): PersianWeekday =
    PersianWeekday.entries.toTypedArray()
        .getOrElse(this.toLocalDate().dayOfWeek.isoDayNumber) { PersianWeekday.UNKNOWN }

/**
 * Formats this PersianDateTime using the DSL builder.
 *
 * Usage:
 * ```kotlin
 * val formatted = persianDate.format {
 *     day = true
 *     monthName = true
 *     year = true
 *     hour = true
 *     minute = true
 * }
 * ```
 */
fun PersianDateTime.format(builderAction: PersianDateTimeFormat.() -> Unit): String {
    val builder = PersianDateTimeFormat()
    builder.dateTime = this
    builder.builderAction()
    return builder.build()
}

/**
 * Returns a date string in `YYYY/MM/DD` format.
 *
 * Usage:
 * ```kotlin
 * val dateStr = persianDate.toDateString()
 * ```
 */
fun PersianDateTime.toDateString(): String =
    "${year.toString().padStart(4,'0')}/${month.toString().padStart(2,'0')}/${day.toString().padStart(2,'0')}"

/**
 * Returns a time string in `HH:MM:SS` format.
 *
 * Usage:
 * ```kotlin
 * val timeStr = persianDate.toTimeString()
 * ```
 */
fun PersianDateTime.toTimeString(): String =
    "${hour.toString().padStart(2,'0')}:${minute.toString().padStart(2,'0')}:${second.toString().padStart(2,'0')}"

/**
 * Returns a datetime string in `YYYY/MM/DD HH:MM:SS` format.
 *
 * Usage:
 * ```kotlin
 * val dateTimeStr = persianDate.toDateTimeString()
 * ```
 */
fun PersianDateTime.toDateTimeString(): String = "${toDateString()} ${toTimeString()}"
