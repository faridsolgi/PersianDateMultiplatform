package com.faridsolgi.persiandatemultiplatform.domain

data class PersianDateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0
)