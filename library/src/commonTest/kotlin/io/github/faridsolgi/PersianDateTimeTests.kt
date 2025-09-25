package io.github.faridsolgi


import io.github.faridsolgi.persiandatetime.converter.dayOfWeek
import io.github.faridsolgi.persiandatetime.converter.dayOfWeekName
import io.github.faridsolgi.persiandatetime.converter.format
import io.github.faridsolgi.persiandatetime.converter.isAfter
import io.github.faridsolgi.persiandatetime.converter.isBefore
import io.github.faridsolgi.persiandatetime.converter.isBetween
import io.github.faridsolgi.persiandatetime.converter.isLeap
import io.github.faridsolgi.persiandatetime.converter.minusDays
import io.github.faridsolgi.persiandatetime.converter.monthLength
import io.github.faridsolgi.persiandatetime.converter.plusDays
import io.github.faridsolgi.persiandatetime.converter.toDateString
import io.github.faridsolgi.persiandatetime.converter.toDateTimeString
import io.github.faridsolgi.persiandatetime.converter.toLocalDate
import io.github.faridsolgi.persiandatetime.converter.toPersianDateTime
import io.github.faridsolgi.persiandatetime.converter.toTimeString
import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PersianDateTimeTests {

    @Test
    fun testGregorianToPersianConversion() {
        val gregorian = LocalDate(2025, 3, 21)
        val persian = gregorian.toPersianDateTime()
        assertEquals(1404, persian.year)
        assertEquals(1, persian.month)
        assertEquals(1, persian.day)
    }

    @Test
    fun testPersianToGregorianConversion() {
        val persian = PersianDateTime(1404, 1, 1)
        val gregorian = persian.toLocalDate()
        assertEquals(LocalDate(2025, 3, 21), gregorian)
    }

    @Test
    fun testDateArithmetic() {
        val persian = PersianDateTime(1404, 1, 1)
        val nextDay = persian.plusDays(1)
        assertEquals(2, nextDay.day)
        val prevDay = persian.minusDays(1)
        assertEquals(30, prevDay.day)
        assertEquals(12, prevDay.month)
    }

    @Test
    fun testComparison() {
        val date1 = PersianDateTime(1404, 1, 1)
        val date2 = PersianDateTime(1404, 1, 2)
        assertTrue(date1.isBefore(date2))
        assertTrue(date2.isAfter(date1))
        assertTrue(date2.isBetween(date1, PersianDateTime(1404, 1, 3)))
    }

    @Test
    fun testLeapYear() {
        assertTrue(PersianDateTime(1399, 1, 1).isLeap())
        assertFalse(PersianDateTime(1400, 1, 1).isLeap())
    }

    @Test
    fun testMonthLength() {
        assertEquals(31, PersianDateTime(1404, 1, 1).monthLength())
        assertEquals(30, PersianDateTime(1404, 7, 1).monthLength())
        assertEquals(29, PersianDateTime(1404, 12, 1).monthLength())
        assertEquals(30, PersianDateTime(1399, 12, 1).monthLength()) // leap year
    }

    @Test
    fun testDayOfWeek() {
        val persian = PersianDateTime(1404, 1, 1) // corresponds to Saturday
        assertEquals(7, persian.dayOfWeek())
        assertEquals("جمعه", persian.dayOfWeekName())
    }

    @Test
    fun testStringFormatting() {
        val persian = PersianDateTime(1404, 1, 1, 15, 30, 45)
        assertEquals("1404/01/01", persian.toDateString())
        assertEquals("15:30:45", persian.toTimeString())
        assertEquals("1404/01/01 15:30:45", persian.toDateTimeString())
        val formatted = persian.format {
            day(); char('/'); month(); char('/'); year(); char(' '); hour(); char(':'); minute()
        }
        assertEquals("01/01/1404 15:30", formatted)
    }
}
