package io.github.faridsolgi.persiandatetime

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PersianDateTimeParseTest {

    @Test
    fun parseShouldHandleYYYYMMDDFormat() {
        val dt = PersianDateTime.parse("1402/05/01")
        assertEquals(1402, dt.year)
        assertEquals(5, dt.month)
        assertEquals(1, dt.day)
        assertEquals(0, dt.hour)
        assertEquals(0, dt.minute)
        assertEquals(0, dt.second)
    }

    @Test
    fun parseShouldHandleYYYYMMDDWithDashFormat() {
        val dt = PersianDateTime.parse("1402-05-01")
        assertEquals(1402, dt.year)
        assertEquals(5, dt.month)
        assertEquals(1, dt.day)
    }

    @Test
    fun parseShouldHandleYYYYMMDDHHMMFormat() {
        val dt = PersianDateTime.parse("1402/05/01 14:30")
        assertEquals(14, dt.hour)
        assertEquals(30, dt.minute)
        assertEquals(0, dt.second)
    }

    @Test
    fun parseShouldHandleYYYYMMDDHHMMSSFormat() {
        val dt = PersianDateTime.parse("1402-05-01 14:30:45")
        assertEquals(14, dt.hour)
        assertEquals(30, dt.minute)
        assertEquals(45, dt.second)
    }

    @Test
    fun parseShouldHandleISOFormat() {
        val dt = PersianDateTime.parse("1402-05-01T14:30:45")
        assertEquals(1402, dt.year)
        assertEquals(5, dt.month)
        assertEquals(1, dt.day)
        assertEquals(14, dt.hour)
        assertEquals(30, dt.minute)
        assertEquals(45, dt.second)
    }

    @Test
    fun parseShouldThrowOnInvalidFormat() {
        assertFailsWith<IllegalArgumentException> {
            PersianDateTime.parse("invalid-date")
        }

        assertFailsWith<IllegalArgumentException> {
            PersianDateTime.parse("1402/05") // missing day
        }

        assertFailsWith<IllegalArgumentException> {
            PersianDateTime.parse("1402-13-01") // invalid month
        }
    }

    @Test
    fun parseShouldHandleOptionalSeconds() {
        val dt = PersianDateTime.parse("1402-05-01 14:30")
        assertEquals(0, dt.second)
    }

    @Test
    fun parseShouldHandleExtraSpaces() {
        val dt = PersianDateTime.parse(" 1402-05-01 14:30:45 ")
        assertEquals(14, dt.hour)
        assertEquals(30, dt.minute)
        assertEquals(45, dt.second)
    }
}
