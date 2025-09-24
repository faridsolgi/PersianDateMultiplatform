package com.faridsolgi.persiandatemultiplatform.converter


import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
import com.faridsolgi.persiandatemultiplatform.converter.PersianDateConverter
import com.faridsolgi.persiandatemultiplatform.util.Constants
import com.faridsolgi.persiandatemultiplatform.util.PersianDateTimeFormat
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus

// -----------------------------
// Conversion Extensions
// -----------------------------

fun LocalDate.toPersianDateTime(hour: Int = 0, minute: Int = 0, second: Int = 0): PersianDateTime {
    val persian = PersianDateConverter.toPersianDate(this)
    return PersianDateTime(persian.year, persian.month, persian.day, hour, minute, second)
}

fun LocalDateTime.toPersianDateTime(): PersianDateTime =
    this.date.toPersianDateTime(hour = this.hour, minute = this.minute, second = this.second)

fun PersianDateTime.toLocalDateTime(): LocalDateTime =
    LocalDateTime(year, month, day, hour, minute, second)

fun PersianDateTime.toGregorian(): LocalDate = PersianDateConverter.toGregorianDate(this)

// -----------------------------
// Arithmetic Extensions
// -----------------------------

fun PersianDateTime.plusDays(days: Int): PersianDateTime {
    val newDate = this.toLocalDateTime().date.plus(days, DateTimeUnit.DAY)
    return newDate.toPersianDateTime(hour, minute, second)
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

fun getLeapFactor(jalaliYear: Int): Int {
    val breaks = Constants.LEAP_BREAKS
    var jp = breaks[0]
    for (j in 1..breaks.lastIndex) {
        val jm = breaks[j]
        val jump = jm - jp
        if (jalaliYear < jm) {
            var n = jalaliYear - jp
            if ((jump - n) < 6) n = n - jump + (jump + 4) / 33 * 33
            var leap = ((n + 1) % 33 - 1) % 4
            if (leap == -1) leap = 4
            return leap
        }
        jp = jm
    }
    return 0
}

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
