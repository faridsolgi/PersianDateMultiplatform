package com.faridsolgi.persiandatemultiplatform.converter

import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

internal object PersianDateConverter {

    fun toPersianDate(date: LocalDate): PersianDateTime {
        val g_d_m = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val year = date.year
        val month = date.month.number
        val day = date.day

        val gy2 = if (month > 2) year + 1 else year
        var days = 355666 + (365 * year) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) +
                ((gy2 + 399) / 400) + day + g_d_m[month - 1]

        var jy = -1595 + 33 * (days / 12053)
        days %= 12053
        jy += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            jy += (days - 1) / 365
            days = (days - 1) % 365
        }

        val jm = if (days < 186) 1 + days / 31 else 7 + (days - 186) / 30
        val jd = if (days < 186) 1 + days % 31 else 1 + (days - 186) % 30

        return PersianDateTime(jy, jm, jd)
    }

    fun toGregorianDate(persianDate: PersianDateTime): LocalDateTime {
        val jy1 = persianDate.year + 1595
        var days = -355668 + (365 * jy1) + (jy1 / 33) * 8 + ((jy1 % 33 + 3) / 4) +
                persianDate.day + if (persianDate.month < 7) (persianDate.month - 1) * 31 else ((persianDate.month - 7) * 30 + 186)

        var gy = 400 * (days / 146097)
        days %= 146097
        if (days > 36524) {
            gy += 100 * (--days / 36524)
            days %= 36524
            if (days >= 365) days++
        }
        gy += 4 * (days / 1461)
        days %= 1461
        if (days > 365) {
            gy += (days - 1) / 365
            days = (days - 1) % 365
        }

        var gd = days + 1
        val sal_a = intArrayOf(
            0, 31,
            if ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0)) 29 else 28,
            31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        )

        var gm = 0
        while (gm < 13 && gd > sal_a[gm]) gd -= sal_a[gm++]

        return LocalDateTime(gy, gm, gd, persianDate.hour, persianDate.minute, persianDate.second)
    }
}