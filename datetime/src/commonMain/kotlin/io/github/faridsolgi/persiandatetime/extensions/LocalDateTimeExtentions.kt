package io.github.faridsolgi.persiandatetime.extensions

import io.github.faridsolgi.persiandatetime.domain.PersianDateTime
import io.github.faridsolgi.persiandatetime.util.Main
import kotlinx.datetime.LocalDateTime

/**
 * Converts this [LocalDateTime] to a [PersianDateTime], preserving the time components.
 *
 * Usage:
 * ```kotlin
 * val localDateTime = LocalDateTime(2023, 9, 28, 14, 30, 0)
 * val persianDate = localDateTime.toPersianDateTime()
 * println(persianDate.toDateTimeString())
 * ```
 *
 * @return The corresponding PersianDateTime with the same hour, minute, and second.
 */
fun LocalDateTime.toPersianDateTime(): PersianDateTime {
    val date = Main.persianDateConverter.fromGregorian(this.date)
    return date.copy(hour = hour, minute = minute, second = second)
}
