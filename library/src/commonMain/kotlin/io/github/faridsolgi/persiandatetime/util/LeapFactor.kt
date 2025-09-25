package io.github.faridsolgi.persiandatetime.util

internal fun getLeapFactor(persianYear: Int): Int {
    val breaks = Constants.LEAP_BREAKS
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