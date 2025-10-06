package io.github.faridsolgi.persiandatetime.data

import io.github.faridsolgi.persiandatetime.domain.IDateConverter
import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

internal object PersianDateConverterImpl : IDateConverter<PersianDateTime> {
    // Constants
    private const val DAYS_IN_33_YEARS = 12053
    private const val DAYS_IN_4_YEARS = 1461
    private const val DAYS_IN_400_YEARS = 146097
    private const val DAYS_IN_100_YEARS = 36524

    // Days until the start of each Gregorian month (non-leap year)
    private val gregorianCumulativeDays = intArrayOf(
        0,   // placeholder for 1-based index
        0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334
    )

    private fun isGregorianLeap(year: Int): Boolean =
        (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

    // ---------------------------
    // Gregorian -> Persian
    // ---------------------------
    override fun fromGregorian(date: LocalDate): PersianDateTime {
        val gy = date.year
        val gm = date.month.number
        val gd = date.day

        val adjustedYear = if (gm > 2) gy + 1 else gy
        var days = 355666 +
                (365 * gy) +
                ((adjustedYear + 3) / 4) -
                ((adjustedYear + 99) / 100) +
                ((adjustedYear + 399) / 400) +
                gd + gregorianCumulativeDays[gm]

        var jy = -1595 + 33 * (days / DAYS_IN_33_YEARS)
        days %= DAYS_IN_33_YEARS

        jy += 4 * (days / DAYS_IN_4_YEARS)
        days %= DAYS_IN_4_YEARS

        if (days > 365) {
            jy += (days - 1) / 365
            days = (days - 1) % 365
        }

        val jm: Int
        val jd: Int
        if (days < 186) {
            jm = 1 + days / 31
            jd = 1 + days % 31
        } else {
            jm = 7 + (days - 186) / 30
            jd = 1 + (days - 186) % 30
        }

        return PersianDateTime(jy, jm, jd)
    }

    // ---------------------------
    // Persian -> Gregorian
    // ---------------------------
    override fun toGregorian(date: PersianDateTime): LocalDateTime {
        val jy = date.year + 1595
        var days = -355668 +
                (365 * jy) +
                (jy / 33) * 8 +
                ((jy % 33 + 3) / 4) +
                date.day +
                if (date.month < 7) (date.month - 1) * 31
                else ((date.month - 7) * 30 + 186)

        var gy = 400 * (days / DAYS_IN_400_YEARS)
        days %= DAYS_IN_400_YEARS

        if (days > DAYS_IN_100_YEARS) {
            gy += 100 * (--days / DAYS_IN_100_YEARS)
            days %= DAYS_IN_100_YEARS
            if (days >= 365) days++
        }

        gy += 4 * (days / DAYS_IN_4_YEARS)
        days %= DAYS_IN_4_YEARS

        if (days > 365) {
            gy += (days - 1) / 365
            days = (days - 1) % 365
        }

        var gd = days + 1
        val gregorianMonthLengths = intArrayOf(
            0,
            31,
            if (isGregorianLeap(gy)) 29 else 28,
            31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        )

        var gm = 1
        while (gm <= 12 && gd > gregorianMonthLengths[gm]) {
            gd -= gregorianMonthLengths[gm]
            gm++
        }

        return LocalDateTime(
            gy, gm, gd,
            date.hour,
            date.minute,
            date.second
        )
    }
}

