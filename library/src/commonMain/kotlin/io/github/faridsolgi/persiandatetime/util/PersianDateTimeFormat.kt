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

    fun hour(pad: Int = 2) {
        parts.add { dateTime.hour.toString().padStart(pad, '0') }
    }

    fun minute(pad: Int = 2) {
        parts.add { dateTime.minute.toString().padStart(pad, '0') }
    }

    fun second(pad: Int = 2) {
        parts.add { dateTime.second.toString().padStart(pad, '0') }
    }

    // Character literal
    fun char(c: Char) {
        parts.add { c.toString() }
    }

    // Month name
    fun monthName() {
        parts.add { dateTime.monthName() }
    }

    // Day of week name (Saturday = 1)
    fun dayOfWeekName() {

        parts.add { dateTime.dayOfWeekName() }
    }

    fun build(): String = parts.joinToString("") { it() }
}