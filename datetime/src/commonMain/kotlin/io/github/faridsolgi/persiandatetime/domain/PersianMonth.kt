package io.github.faridsolgi.persiandatetime.domain

/**
 * Represents the months in the Persian calendar.
 *
 * Each month has a [displayName] in Persian.
 *
 * Usage:
 * ```kotlin
 * val month = PersianMonth.MEHR
 * println(month.displayName) // Outputs: "مهر"
 *
 * val fromName = PersianMonth.fromDisplayName("دی")
 * println(fromName) // Outputs: DEY
 * ```
 */
enum class PersianMonth(val displayName: String) {
    UNKNOWN("نامعلوم"),
    FARVARDIN("فروردین"),
    ORDIBEHESHT("اردیبهشت"),
    KHORDAD("خرداد"),
    TIR("تیر"),
    MORDAD("مرداد"),
    SHAHRIVAR("شهریور"),
    MEHR("مهر"),
    ABAN("آبان"),
    AZAR("آذر"),
    DEY("دی"),
    BAHMAN("بهمن"),
    ESFAND("اسفند");

    companion object {
        fun fromDisplayName(name: String): PersianMonth =
            values().find { it.displayName == name } ?: UNKNOWN
    }
}