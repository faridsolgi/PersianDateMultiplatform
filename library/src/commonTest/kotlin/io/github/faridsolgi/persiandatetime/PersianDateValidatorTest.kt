package io.github.faridsolgi.persiandatetime

import io.github.faridsolgi.persiandatetime.util.PersianDateValidator
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PersianDateValidatorTest {

    @Test
    fun `validateMonth should allow months 1 to 12`() {
        for (month in 1..12) {
            // Should not throw
            PersianDateValidator.validateMonth(month)
        }
    }

    @Test
    fun `validateMonth should throw for invalid months`() {
        val invalidMonths = listOf(0, -1, 13, 100)
        for (month in invalidMonths) {
            val exception = assertFailsWith<IllegalArgumentException> {
                PersianDateValidator.validateMonth(month)
            }
            assertTrue(exception.message!!.contains("ماه نامعتبر"))
        }
    }

    @Test
    fun `validateDay should allow valid days`() {
        // Month 1-6 (31 days)
        PersianDateValidator.validateDay(1402, 1, 31)
        // Month 7-11 (30 days)
        PersianDateValidator.validateDay(1402, 7, 30)
        // Month 12 non-leap (29 days)
        PersianDateValidator.validateDay(1402, 12, 29)
        // Month 12 leap year (30 days)
        PersianDateValidator.validateDay(1403, 12, 30) // assuming 1403 is leap
    }

    @Test
    fun `validateDay should throw for invalid days`() {
        val invalidDays = listOf(0, -5, 32)
        for (day in invalidDays) {
            val exception = assertFailsWith<IllegalArgumentException> {
                PersianDateValidator.validateDay(1402, 1, day)
            }
            assertTrue(exception.message!!.contains("روز نامعتبر"))
        }
    }

    @Test
    fun `validateTime should allow valid time`() {
        PersianDateValidator.validateTime(0, 0, 0)
        PersianDateValidator.validateTime(23, 59, 59)
    }

    @Test
    fun `validateTime should throw for invalid time`() {
        val invalidTimes = listOf(
            Triple(-1, 0, 0),
            Triple(24, 0, 0),
            Triple(0, -1, 0),
            Triple(0, 60, 0),
            Triple(0, 0, -1),
            Triple(0, 0, 60)
        )
        for ((h, m, s) in invalidTimes) {
            assertFailsWith<IllegalArgumentException> {
                PersianDateValidator.validateTime(h, m, s)
            }
        }
    }

    @Test
    fun `validateDateTime should validate correct datetime`() {
        PersianDateValidator.validateDateTime(1402, 6, 30, 12, 30, 30)
    }

    @Test
    fun `validateDateTime should throw for invalid datetime`() {
        assertFailsWith<IllegalArgumentException> {
            PersianDateValidator.validateDateTime(1402, 13, 32, 25, 61, 61)
        }
    }
}
