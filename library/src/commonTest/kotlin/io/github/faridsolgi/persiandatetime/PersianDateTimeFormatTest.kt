package io.github.faridsolgi.persiandatetime

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.util.PersianDateTimeFormat

import kotlin.test.Test
import kotlin.test.assertEquals

class PersianDateTimeFormatTest {

    private fun fakeDateTime(): PersianDateTime {
        return PersianDateTime(
            year = 1403,
            month = 7,
            day = 3,
            hour = 14,
            minute = 5,
            second = 9
        )
    }

    @Test
    fun `format year month day`() {
        val fmt = PersianDateTimeFormat().apply {
            dateTime = fakeDateTime()
            year(); char('-'); month(); char('-'); day()
        }
        assertEquals("1403-07-03", fmt.build())
    }

    @Test
    fun `format 24h time`() {
        val fmt = PersianDateTimeFormat().apply {
            dateTime = fakeDateTime()
            hour24(); char(':'); minute(); char(':'); second()
        }
        assertEquals("14:05:09", fmt.build())
    }

    @Test
    fun `format 12h time with AM PM`() {
        val fmt = PersianDateTimeFormat().apply {
            dateTime = fakeDateTime()
            hour12(); char(':'); minute(); char(' '); amPm()
        }
        assertEquals("02:05 PM", fmt.build())
    }

    @Test
    fun `format 12h time with lowercase am pm`() {
        val fmt = PersianDateTimeFormat().apply {
            dateTime = fakeDateTime()
            hour12(); char(':'); minute(); char(' '); amPm(false)
        }
        assertEquals("02:05 pm", fmt.build())
    }

    @Test
    fun `format with month name and day of week`() {
        val fmt = PersianDateTimeFormat().apply {
            dateTime = fakeDateTime()
            dayOfWeekName(); char(','); char(' '); monthName()
        }
        assertEquals("سه‌شنبه, مهر", fmt.build())
    }
}
