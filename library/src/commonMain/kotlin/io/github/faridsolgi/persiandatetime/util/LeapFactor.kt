package io.github.faridsolgi.persiandatetime.util

internal object PersianLeap {
    fun getLeapFactor(persianYear: Int): Int {
        val breaks = BREAKS
        var jp = breaks[0]
        for (j in 1..breaks.lastIndex) {
            val jm = breaks[j]
            val jump = jm - jp
            if (persianYear < jm) {
                var n = persianYear - jp
                if ((jump - n) < 6) n = n - jump + (jump + 4) / 33 * 33
                var leap = ((n + 1) % 33 - 1) % 4
                if (leap == -1) leap = 4
                return leap
            }
            jp = jm
        }
        return 0
    }

    val BREAKS = listOf(
        -61, 9, 38, 199, 426, 686, 756, 818, 1111,
        1181, 1210, 1635, 2060, 2097, 2192, 2262,
        2324, 2394, 2456, 3178
    )

}
