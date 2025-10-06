package io.github.faridsolgi.persiandatetime.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

internal interface IDateConverter<T> {
    fun toGregorian(date: T): LocalDateTime
    fun fromGregorian(date: LocalDate): T
}