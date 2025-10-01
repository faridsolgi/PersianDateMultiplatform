package io.github.faridsolgi.persiandatetime.domain

import io.github.faridsolgi.persiandatetime.extensions.toPersianDateTime
import io.github.faridsolgi.persiandatetime.util.PersianDateValidator
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


/**
 * Represents a date and time in the Persian calendar.
 *
 * @property year The year in the Persian calendar (e.g., 1402).
 * @property month The month of the year (1 = Farvardin, 12 = Esfand).
 * @property day The day of the month (1-based).
 * @property hour The hour of the day (0–23). Defaults to 0.
 * @property minute The minute of the hour (0–59). Defaults to 0.
 * @property second The second of the minute (0–59). Defaults to 0.
 *
 * @throws IllegalArgumentException if the date or time values are invalid according
 *         to the Persian calendar.
 */
@Serializable(with = PersianDateTimeSerializer::class)
data class PersianDateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
) {

    init {
        PersianDateValidator.validateDateTime(year, month, day, hour, minute, second)
    }

    companion object {
        /**
         * Parse PersianDateTime from a string.
         *
         * Supported formats:
         * - "yyyy/MM/dd"
         * - "yyyy-MM-dd"
         * - "yyyy/MM/dd HH:mm"
         * - "yyyy-MM-dd HH:mm"
         * - "yyyy/MM/dd HH:mm:ss"
         * - "yyyy-MM-dd HH:mm:ss"
         * - "yyyy-MM-ddTHH:mm:ss"
         *
         * @param input The date string to parse.
         * @return A valid [PersianDateTime] instance.
         * @throws IllegalArgumentException if the input format is invalid.
         */
        fun parse(input: String): PersianDateTime {
            val cleanInput = input.trim()

            // Split date and time, supporting " " or "T" separator
            val parts = cleanInput.split(' ', 'T')
            val datePart = parts.getOrNull(0) ?: throw IllegalArgumentException("Empty date string")
            val timePart = parts.getOrNull(1) ?: "00:00:00"

            // Detect date separator
            val dateSeparators = arrayOf("/", "-")
            val dateElements = dateSeparators.firstNotNullOfOrNull { sep ->
                datePart.split(sep).takeIf { it.size == 3 }
            } ?: throw IllegalArgumentException("Invalid date format: $input")

            val (y, m, d) = dateElements.map { it.toInt() }

            // Parse time
            val timeElements = timePart.split(":").map { it.toIntOrNull() ?: 0 }
            val h = timeElements.getOrElse(0) { 0 }
            val min = timeElements.getOrElse(1) { 0 }
            val s = timeElements.getOrElse(2) { 0 }

            return PersianDateTime(y, m, d, h, min, s)
        }


        @OptIn(ExperimentalTime::class)
        fun parse(
            timeStampMilli: Long,
            timeZone: TimeZone = TimeZone.currentSystemDefault(),
        ): PersianDateTime {
            val instant = Instant.fromEpochMilliseconds(timeStampMilli)
            return instant.toLocalDateTime(timeZone).toPersianDateTime()
        }
    }
}