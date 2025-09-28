package io.github.faridsolgi.persiandatetime.util

import io.github.faridsolgi.persiandatetime.converter.persianDayOfWeek
import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.domain.PersianMonthDay

//maybe remove
internal class PersianDateHelper {

    fun getPersianMonth(year: Int, month: Int): List<PersianMonthDay> {
        val daysInMonth = when {
            month <= 6 -> 31
            month <= 11 -> 30
            else -> if (PersianLeap.getLeapFactor(year) == 0) 30 else 29
        }

        return (1..daysInMonth).map { day ->
            val persianDate = PersianDateTime(year, month, day)
            val gregorian = Main.persianDateConverter.toGregorian(persianDate)
            val weekday = persianDate.persianDayOfWeek()
            PersianMonthDay(persianDate, weekday, gregorian)
        }
    }
}