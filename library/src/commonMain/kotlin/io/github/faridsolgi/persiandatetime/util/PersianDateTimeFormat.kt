package io.github.faridsolgi.persiandatetime.util


import io.github.faridsolgi.persiandatetime.converter.dayOfWeekName
import io.github.faridsolgi.persiandatetime.converter.monthName
import io.github.faridsolgi.persiandatetime.domain.PersianDateTime

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
    fun amPm(upper: Boolean = true) {
        parts.add {
            val marker = if (dateTime.hour < 12) "AM" else "PM"
            if (upper) marker else marker.lowercase()
        }
    }

    // Character literal
    fun char(c: Char) {
        parts.add { c.toString() }
    }

    // Month name
    fun monthName() {
        parts.add { dateTime.monthName() }
    }

    // Day of week name
    fun dayOfWeekName() {
        parts.add { dateTime.dayOfWeekName() }
    }

    fun build(): String = parts.joinToString("") { it() }
}