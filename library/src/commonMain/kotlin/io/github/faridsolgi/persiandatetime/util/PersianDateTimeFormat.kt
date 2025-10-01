package io.github.faridsolgi.persiandatetime.util


import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.extensions.persianDayOfWeek
import io.github.faridsolgi.persiandatetime.extensions.persianMonth

/**
 * A DSL builder for formatting [PersianDateTime] objects into custom string representations.
 *
 * You can selectively add year, month, day, hour, minute, second, AM/PM markers,
 * literal characters, month names, and day-of-week names.
 *
 * Example usage:
 * ```kotlin
 * val persianDate = PersianDateTime(1402, 7, 15, 14, 30, 0)
 * val formatted = PersianDateTimeFormat().apply {
 *     dateTime = persianDate
 *     day(pad = 2)
 *     char('/')
 *     month(pad = 2)
 *     char('/')
 *     year(pad = 4)
 *     char(' ')
 *     hour24()
 *     char(':')
 *     minute()
 *     char(':')
 *     second()
 *     char(' ')
 *     amPm()
 * }.build()
 * println(formatted) // Outputs: "15/07/1402 14:30:00 PM"
 * ```
 */
class PersianDateTimeFormat {
    private val parts = mutableListOf<() -> String>()

    lateinit var dateTime: PersianDateTime

    // Numeric parts
    fun year(pad: Int = 4) {
        parts.add { dateTime.year.toString().padStart(pad, '0') }
    }

    fun month(pad: Int = 2) {
        parts.add { dateTime.month.toString().padStart(pad, '0') }
    }

    fun day(pad: Int = 2) {
        parts.add { dateTime.day.toString().padStart(pad, '0') }
    }

    // 24-hour format
    fun hour24(pad: Int = 2) {
        parts.add { dateTime.hour.toString().padStart(pad, '0') }
    }

    // 12-hour format
    fun hour12(pad: Int = 2) {
        parts.add {
            val h = dateTime.hour % 12
            val display = if (h == 0) 12 else h
            display.toString().padStart(pad, '0')
        }
    }

    fun minute(pad: Int = 2) {
        parts.add { dateTime.minute.toString().padStart(pad, '0') }
    }

    fun second(pad: Int = 2) {
        parts.add { dateTime.second.toString().padStart(pad, '0') }
    }

    // AM/PM marker
    fun amPm() {
        parts.add {
            if (dateTime.hour < 12) "ق.‌ظ" else "ب.‌ظ"
        }
    }

    // Character literal
    fun char(c: Char) {
        parts.add { c.toString() }
    }

    // Month name
    fun monthName() {
        parts.add { dateTime.persianMonth().displayName }
    }

    // Day of week name
    fun dayOfWeekName() {
        parts.add { dateTime.persianDayOfWeek().displayName }
    }

    fun build(): String = parts.joinToString("") { it() }
}