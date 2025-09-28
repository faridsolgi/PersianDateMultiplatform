package io.github.faridsolgi.persiandatetime.util

object PersianDateValidator {

    fun validateMonth(month: Int) {
        require(month in 1..12) { "ماه نامعتبر: $month" }
    }

    fun validateDay(year: Int, month: Int, day: Int) {
        val maxDay = when (month) {
            in 1..6 -> 31
            in 7..11 -> 30
            12 -> if ((PersianLeap.getLeapFactor(year) == 0)) 30 else 29 //leapFactor == 0 is leap year
            else -> throw IllegalArgumentException("ماه نامعتبر: $month")
        }
        require(day in 1..maxDay) { "روز نامعتبر: $day برای ماه $month" }
    }

    fun validateTime(hour: Int, minute: Int, second: Int) {
        require(hour in 0..23) { "ساعت نامعتبر: $hour" }
        require(minute in 0..59) { "دقیقه نامعتبر: $minute" }
        require(second in 0..59) { "ثانیه نامعتبر: $second" }
    }

    fun validateDateTime(
        year: Int,
        month: Int,
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0,
    ) {
        validateMonth(month)
        validateDay(year, month, day)
        validateTime(hour, minute, second)
    }

}