package io.github.faridsolgi.persiandatetime.util

import io.github.faridsolgi.persiandatetime.data.PersianDateConverterImpl

internal object Main {
    val persianDateConverter by lazy {
        PersianDateConverterImpl
    }

}