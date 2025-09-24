package com.faridsolgi.persiandatemultiplatform.util


object Constants {

    // Month names (1-12)
    val MONTH_NAMES = listOf(
        "نامعلوم",
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )

    // Week day names (1=یک‌شنبه, 7=شنبه)
    val WEEK_NAMES = listOf(
        "نامعلوم",   // 0 (unused)
        "شنبه",      // 1
        "یک‌شنبه",   // 2
        "دوشنبه",   // 3
        "سه‌شنبه",  // 4
        "چهارشنبه", // 5
        "پنجشنبه",  // 6
        "جمعه"       // 7
    )

    // Jalali leap-year break points
    val LEAP_BREAKS = intArrayOf(
        -61, 9, 38, 199, 426, 686, 756, 818, 1111,
        1181, 1210, 1635, 2060, 2097, 2192, 2262,
        2324, 2394, 2456, 3178
    )
}