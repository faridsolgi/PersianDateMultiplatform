package io.github.faridsolgi.persiandatetime.converter


import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.util.Constants
import io.github.faridsolgi.persiandatetime.util.PersianDateTimeFormat
import io.github.faridsolgi.persiandatetime.util.getLeapFactor
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus

fun PersianDateTime.toLocalDate(): LocalDate = PersianDateConverter.toGregorianDate(this).date
fun PersianDateTime.toLocalDateTime(): LocalDateTime = PersianDateConverter.toGregorianDate(this)


// -----------------------------
// Arithmetic Extensions
// -----------------------------


fun PersianDateTime.plusDays(days: Int): PersianDateTime {
    val localDateTime = this.toLocalDateTime()
    val newDate = localDateTime.date.plus(days, DateTimeUnit.DAY)
    return newDate.toPersianDateTime(this.hour, this.minute, this.second)
}

fun PersianDateTime.minusDays(days: Int): PersianDateTime = plusDays(-days)

// -----------------------------
// Comparison Extensions
// -----------------------------

fun PersianDateTime.isBefore(other: PersianDateTime) = this.toLocalDateTime() < other.toLocalDateTime()
fun PersianDateTime.isAfter(other: PersianDateTime) = this.toLocalDateTime() > other.toLocalDateTime()
fun PersianDateTime.isBetween(start: PersianDateTime, end: PersianDateTime) =
    this.toLocalDateTime() >= start.toLocalDateTime() && this.toLocalDateTime() <= end.toLocalDateTime()

// -----------------------------
// Month, Day, Leap Extensions
// -----------------------------

fun PersianDateTime.isLeap(): Boolean = getLeapFactor(year) == 0

fun PersianDateTime.monthLength(): Int = when {
    month < 7 -> 31
    month < 12 -> 30
    month == 12 -> if (this.isLeap()) 30 else 29
    else -> 0
}

fun PersianDateTime.monthName(): String = Constants.MONTH_NAMES.getOrElse(month) { "نامعلوم" }

fun PersianDateTime.dayOfWeek(): Int {
    val isoDay = this.toLocalDateTime().date.dayOfWeek.isoDayNumber
    return when (isoDay) { 6 -> 1; 7 -> 2; 1 -> 3; 2 -> 4; 3 -> 5; 4 -> 6; 5 -> 7; else -> 0 }
}

fun PersianDateTime.dayOfWeekName(): String = Constants.WEEK_NAMES.getOrElse(dayOfWeek()) { "نامعلوم" }


// -----------------------------
// Formatting DSL
// -----------------------------

fun PersianDateTime.format(builderAction: PersianDateTimeFormat.() -> Unit): String {
    val builder = PersianDateTimeFormat()
    builder.dateTime = this
    builder.builderAction()
    return builder.build()
}

// -----------------------------
// Simple string formatting helpers
// -----------------------------

fun PersianDateTime.toDateString(): String =
    "${year.toString().padStart(4,'0')}/${month.toString().padStart(2,'0')}/${day.toString().padStart(2,'0')}"

fun PersianDateTime.toTimeString(): String =
    "${hour.toString().padStart(2,'0')}:${minute.toString().padStart(2,'0')}:${second.toString().padStart(2,'0')}"

fun PersianDateTime.toDateTimeString(): String = "${toDateString()} ${toTimeString()}"