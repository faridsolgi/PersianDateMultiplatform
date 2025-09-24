package com.faridsolgi.persiandatemultiplatform

import com.faridsolgi.persiandatemultiplatform.converter.dayOfWeek
import com.faridsolgi.persiandatemultiplatform.converter.isLeap
import com.faridsolgi.persiandatemultiplatform.converter.minusDays
import com.faridsolgi.persiandatemultiplatform.converter.monthLength
import com.faridsolgi.persiandatemultiplatform.converter.plusDays
import com.faridsolgi.persiandatemultiplatform.converter.toDateString
import com.faridsolgi.persiandatemultiplatform.converter.toLocalDate
import com.faridsolgi.persiandatemultiplatform.converter.toPersianDateTime
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PersianDateTimeParameterizedTests {

    private val leapYears = listOf(1395, 1399, 1403, 1408)
    private val nonLeapYears = listOf(1400, 1401, 1402, 1404)

    @Test
    fun testLeapYears() {
        leapYears.forEach { year ->
            val persian = PersianDateTime(year, 1, 1)
            assertTrue(persian.isLeap(), "$year should be leap")
        }
        nonLeapYears.forEach { year ->
            val persian = PersianDateTime(year, 1, 1)
            assertFalse(persian.isLeap(), "$year should not be leap")
        }
    }

    @Test
    fun testMonthLengths() {
        val monthLengthsLeapYear = listOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30)
        val monthLengthsCommonYear = listOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)

        // Leap year
        monthLengthsLeapYear.forEachIndexed { index, length ->
            val persian = PersianDateTime(1399, index + 1, 1)
            assertEquals(
                length,
                persian.monthLength(),
                "Month ${index + 1} length incorrect for leap year"
            )
        }

        // Non-leap year
        monthLengthsCommonYear.forEachIndexed { index, length ->
            val persian = PersianDateTime(1400, index + 1, 1)
            assertEquals(
                length,
                persian.monthLength(),
                "Month ${index + 1} length incorrect for common year"
            )
        }
    }

    @Test
    fun testDayOfWeekConsistency() {
        // Known mapping: 1404/01/01 = Saturday
        val knownDates = mapOf(
            PersianDateTime(1404, 1, 1) to 7,
            PersianDateTime(1404, 1, 2) to 1,
            PersianDateTime(1404, 1, 3) to 2,
            PersianDateTime(1404, 1, 4) to 3,
            PersianDateTime(1404, 1, 5) to 4,
            PersianDateTime(1404, 1, 6) to 5,
            PersianDateTime(1404, 1, 7) to 6
        )

        knownDates.forEach { (date, expectedDay) ->
            assertEquals(
                expectedDay,
                date.dayOfWeek(),
                "Day of week mismatch for ${date.toDateString()}"
            )
        }
    }

    @Test
    fun testGregorianConversionRoundTrip() {
        val datesToTest = listOf(
            LocalDate(2020, 3, 20),
            LocalDate(2021, 3, 21),
            LocalDate(2022, 12, 31),
            LocalDate(2025, 3, 21)
        )

        datesToTest.forEach { gregorian ->
            val persian = gregorian.toPersianDateTime()
            val backToGregorian = persian.toLocalDate()
            assertEquals(gregorian, backToGregorian, "Round-trip conversion failed for $gregorian")
        }
    }

    @Test
    fun testDateArithmeticAcrossMonths() {
        val persian = PersianDateTime(1404, 1, 31)
        val nextDay = persian.plusDays(1)
        assertEquals(1, nextDay.day)
        assertEquals(2, nextDay.month)

        val prevDay = PersianDateTime(1404, 7, 1).minusDays(1)
        assertEquals(31, prevDay.day)
        assertEquals(6, prevDay.month)
    }
}
