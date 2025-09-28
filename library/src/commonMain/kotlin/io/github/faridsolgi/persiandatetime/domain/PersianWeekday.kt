package io.github.faridsolgi.persiandatetime.domain

/**
 * Represents the days of the week in the Persian calendar.
 *
 * Each day has a [displayName] in Persian and a [number] according to the Persian convention.
 *
 * Days are numbered as follows:
 * 1 -> Saturday (SHANBEH)
 * 2 -> Sunday (YEK_SHANBEH)
 * 3 -> Monday (DO_SHANBEH)
 * 4 -> Tuesday (SEH_SHANBEH)
 * 5 -> Wednesday (CHAHAR_SHANBEH)
 * 6 -> Thursday (PANJ_SHANBEH)
 * 7 -> Friday (JOMEH)
 * 0 -> UNKNOWN
 *
 * Usage:
 * ```kotlin
 * val day = PersianWeekday.SEH_SHANBEH
 * println(day.displayName) // Outputs: "سه‌شنبه"
 *
 * val fromName = PersianWeekday.fromDisplayName("جمعه")
 * println(fromName) // Outputs: JOMEH
 *
 * val fromNumber = PersianWeekday.fromNumber(1)
 * println(fromNumber) // Outputs: SHANBEH
 * ```
 */
enum class PersianWeekday(val displayName: String, val number: Int) {
    UNKNOWN("نامعلوم", 0),
    DO_SHANBEH("دوشنبه", 3),
    SEH_SHANBEH("سه‌شنبه", 4),
    CHAHAR_SHANBEH("چهارشنبه", 5),
    PANJ_SHANBEH("پنجشنبه", 6),
    JOMEH("جمعه", 7),
    SHANBEH("شنبه", 1),
    YEK_SHANBEH("یک‌شنبه", 2);

    companion object {
        fun fromDisplayName(name: String): PersianWeekday =
            entries.find { it.displayName == name } ?: UNKNOWN

        fun fromNumber(num: Int): PersianWeekday =
            entries.find { it.number == num } ?: UNKNOWN
    }
}

