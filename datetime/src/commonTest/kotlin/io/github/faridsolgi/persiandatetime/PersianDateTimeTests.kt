package io.github.faridsolgi.persiandatetime


import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.extensions.format
import io.github.faridsolgi.persiandatetime.extensions.isAfter
import io.github.faridsolgi.persiandatetime.extensions.isBefore
import io.github.faridsolgi.persiandatetime.extensions.isBetween
import io.github.faridsolgi.persiandatetime.extensions.isLeapYear
import io.github.faridsolgi.persiandatetime.extensions.minus
import io.github.faridsolgi.persiandatetime.extensions.monthLength
import io.github.faridsolgi.persiandatetime.extensions.persianDayOfWeek
import io.github.faridsolgi.persiandatetime.extensions.plus
import io.github.faridsolgi.persiandatetime.extensions.toDateString
import io.github.faridsolgi.persiandatetime.extensions.toDateTimeString
import io.github.faridsolgi.persiandatetime.extensions.toLocalDate
import io.github.faridsolgi.persiandatetime.extensions.toPersianDateTime
import io.github.faridsolgi.persiandatetime.extensions.toTimeString
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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
    fun testLocalDateTimeToPersianConversion() {
        val gregorian = LocalDateTime(
            year = 2025,
            month = 3,
            day = 21,
            hour = 22,
            minute = 15,
            second = 45
        )
        val persian = gregorian.toPersianDateTime()
        assertEquals(1404, persian.year)
        assertEquals(1, persian.month)
        assertEquals(1, persian.day)
        assertEquals(22, persian.hour)
        assertEquals(15, persian.minute)
        assertEquals(45, persian.second)
    }

    @Test
    fun testPersianToGregorianConversion() {
        val persian = PersianDateTime(1404, 1, 1)
        val gregorian = persian.toLocalDate()
        assertEquals(LocalDate(2025, 3, 21), gregorian)
    }

    @Test
    fun testDateArithmetic() {
        val persian = PersianDateTime(1404, 1, 10)
        val nextDay = persian.plus(1, DateTimeUnit.DAY)
        assertEquals(11, nextDay.day)
        val prevDay = persian.minus(1, DateTimeUnit.DAY)
        assertEquals(9, prevDay.day)
        assertEquals(1, prevDay.month)
    }

    @Test
    fun testDateArithmetic2() {
        val persian = PersianDateTime(1404, 1, 10)
        val nextDay = persian.plus(DatePeriod(days = 1))
        assertEquals(11, nextDay.day)
        val prevDay = persian.minus(DatePeriod(days = 1))
        assertEquals(9, prevDay.day)
        assertEquals(1, prevDay.month)
    }

    @Test
    fun testDateArithmetic3() {
        val first = PersianDateTime(1404, 1, 10)
        val second = PersianDateTime(1403, 1, 10)
        val period = first.minus(second)
        assertEquals(1, period.years)
        assertEquals(1, period.days)
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
        assertTrue(PersianDateTime(1399, 1, 1).isLeapYear())
        assertFalse(PersianDateTime(1400, 1, 1).isLeapYear())
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
        val persian = PersianDateTime(1404, 1, 1) // corresponds to friday
        assertEquals(7, persian.persianDayOfWeek().number)
        assertEquals("جمعه", persian.persianDayOfWeek().displayName)
    }

    @Test
    fun testStringFormatting() {
        val persian = PersianDateTime(1404, 1, 1, 15, 30, 45)
        assertEquals("1404/01/01", persian.toDateString())
        assertEquals("15:30:45", persian.toTimeString())
        assertEquals("1404/01/01 15:30:45", persian.toDateTimeString())
        val formatted = persian.format {
            day(); char('/'); month(); char('/'); year(); char(' '); hour24(); char(':'); minute()
        }
        assertEquals("01/01/1404 15:30", formatted)
    }
}
