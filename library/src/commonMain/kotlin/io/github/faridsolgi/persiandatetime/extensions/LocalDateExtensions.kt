package io.github.faridsolgi.persiandatetime.extensions

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.util.Main
import kotlinx.datetime.LocalDate

/**
 * Converts this [LocalDate] to a [PersianDateTime], optionally setting the time.
 *
 * Usage:
 * ```kotlin
 * val localDate = LocalDate(2023, 9, 28)
 * val persianDate = localDate.toPersianDateTime(hour = 14, minute = 30)
 * println(persianDate.toDateTimeString())
 * ```
 *
 * @param hour The hour to set in the resulting PersianDateTime (default is 0).
 * @param minute The minute to set in the resulting PersianDateTime (default is 0).
 * @param second The second to set in the resulting PersianDateTime (default is 0).
 * @return The corresponding PersianDateTime with the specified time.
 */
fun LocalDate.toPersianDateTime(hour: Int = 0, minute: Int = 0, second: Int = 0): PersianDateTime {
    val date = Main.persianDateConverter.fromGregorian(this)
    return date.copy(hour = hour, minute = minute, second = second)
}
