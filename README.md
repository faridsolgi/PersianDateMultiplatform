# PersianDateMultiplatform

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Compose-Multiplatform-blueviolet.svg)](https://kotlinlang.org/lp/multiplatform/)
![Android](https://img.shields.io/badge/Android-✔-green.svg)
![iOS](https://img.shields.io/badge/iOS-✔-blue.svg)
![JVM](https://img.shields.io/badge/JVM-✔-orange.svg)
![WASM](https://img.shields.io/badge/WASM-✔-purple.svg)


**PersianDateMultiplatform** is a Kotlin Multiplatform library for working with the Persian (Jalali/Shamsi) calendar across Android, iOS, Desktop (JVM), and Web (Kotlin/Wasm). The library provides utilities for conversion, formatting, and manipulation of Persian dates, with support for leap years, month and weekday names, and integration into Compose Multiplatform projects.


Note: This library depends on kotlinx-datetime for date-time operations, so make sure to include it in your project dependencies.



## Features

* **Persian Date Conversion**: Convert between Gregorian and Persian date representations.
* **Date Formatting**: Format Persian date/time objects to strings (date, time, date-time).
* **Leap Year Calculations**: Accurately determine leap years in the Jalali calendar.
* **Month/Weekday Names**: Get Persian month and weekday names.
* **Multiplatform Support**: Use in Android, iOS, Desktop (JVM), and Web (Kotlin/Wasm).
* **DSL for Formatting**: Easy-to-use builder for custom date/time formats.
* **Integration Samples**: Compose Multiplatform project structure for real-world apps.



## Getting Started

* **Kotlin Multiplatform Projects (Common Main)**

 ![Version](https://img.shields.io/badge/version-0.1.0-green.svg)


Add the dependency to your `commonMain` source set in your `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Add kotlinx-datetime first
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:<version>")
                
                // Then your PersianDateTime library
                implementation("io.github.faridsolgi:persiandatetime:<version>")
            }
        }
    }
}
```

* **Android Native Projects**

For Android-native projects, use the dedicated Android artifact:

```kotlin
dependencies {
    // Add kotlinx-datetime first
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:<version>")
    
    // Then the PersianDateTime Android artifact
    implementation("io.github.faridsolgi:persianDateTime-android:<version>")
}
```

You can also get the library from Maven Central: [PersianDateTime on Maven Central](https://central.sonatype.com/namespace/io.github.faridsolgi) or clone and include the library module directly.





## Usage

### Basic Persian Date Representation

```kotlin
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime

// Create a date only
val persianDate = PersianDateTime(year = 1402, month = 7, day = 1)

// Create a date with time
val persianDateTime = PersianDateTime(
    year = 1402,
    month = 7,
    day = 1,
    hour = 14,
    minute = 30,
    second = 45
)
```

### Parsing Persian Dates from String

```kotlin
// Parse date only
val parsedDate = PersianDateTime.parse("1402/07/01")

// Parse date with time
val parsedDateTime = PersianDateTime.parse("1402-07-01 14:30:45")
```

### Additional Examples

```kotlin
// Accessing date components
println(parsedDate.year)   // 1402
println(parsedDate.month)  // 7
println(parsedDate.day)    // 1

// Formatting a Persian date to string
val formatted = parsedDateTime.toString()
println(formatted) // Example: "1402-07-01 14:30:45"
```

### Extension Functions Usage

#### Conversion Extensions

Convert `LocalDate` or `LocalDateTime` to a Persian date:

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.toPersianDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

val gregorianDate = LocalDate(2023, 9, 24)
val persianDate = gregorianDate.toPersianDateTime() // PersianDateTime instance

val gregorianDateTime = LocalDateTime(2023, 9, 24, 15, 20, 0)
val persianDateTime = gregorianDateTime.toPersianDateTime()
```

Convert Persian date back to Gregorian:

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.toGregorian

val gregorian = persianDate.toLocalDate()
val gregorianWithTime = persianDate.toLocalDateTime()
```

#### Current Date/Time Utilities

You can get the current Persian date for a specific time zone:

```kotlin
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import com.faridsolgi.persiandatemultiplatform.converter.nowInTehran
import com.faridsolgi.persiandatemultiplatform.converter.nowPersianDate

// Get the current date in Tehran's timezone
val nowInTehran = Clock.System.nowInTehran
println("Current Persian date in Tehran: ${nowInTehran.toDateString()}")

// Get the current date for a different time zone, e.g., "Europe/Paris"
val nowInParis = Clock.System.nowPersianDate(TimeZone.of("Europe/Paris"))
println("Current Persian date in Paris: ${nowInParis.toDateString()}")
```

#### Arithmetic Extensions

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.plusDays
import com.faridsolgi.persiandatemultiplatform.converter.minusDays

val nextWeek = persianDate.plusDays(7)
val yesterday = persianDate.minusDays(1)
```

#### Comparison Extensions

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.isBefore
import com.faridsolgi.persiandatemultiplatform.converter.isAfter
import com.faridsolgi.persiandatemultiplatform.converter.isBetween

if (persianDate.isBefore(nextWeek)) { /* ... */ }
if (persianDate.isAfter(yesterday)) { /* ... */ }
if (persianDate.isBetween(yesterday, nextWeek)) { /* ... */ }
```

#### Month, Day, Leap Extensions

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.isLeap
import com.faridsolgi.persiandatemultiplatform.converter.monthLength
import com.faridsolgi.persiandatemultiplatform.converter.monthName
import com.faridsolgi.persiandatemultiplatform.converter.dayOfWeekName

println(persianDate.isLeap())        // true/false
println(persianDate.monthLength())   // 31, 30, or 29
println(persianDate.persianMonth().displayName)     // "مهر" (Mehr)
println(persianDate.persianDayOfWeek().displayName) // "سه‌شنبه" (Tuesday)
```

#### Formatted String Date Extensions

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.toDateString
import com.faridsolgi.persiandatemultiplatform.converter.toTimeString
import com.faridsolgi.persiandatemultiplatform.converter.toDateTimeString

println(persianDate.toDateString())      // "1402/07/02"
println(persianDate.toTimeString())      // "00:00:00"
println(persianDate.toDateTimeString())  // "1402/07/02 00:00:00"
```

#### Custom Formatting DSL

The formatting DSL supports full date/time patterns:

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.format

val custom = persianDate.format {
    day()
    char('/')
    month()
    char('/')
    year()
    char(' ')
    hour12()
    char(':')
    minute()
    amPm()
}
println(custom) // "02/07/1402 03:45PM"
```

### Formatting DSL Reference

| Function          | Description                            | Example Output |
| ----------------- | -------------------------------------- | -------------- |
| `year(pad)`       | Year with optional padding (default 4) | 1402           |
| `month(pad)`      | Month number with optional padding (2) | 07             |
| `day(pad)`        | Day of month with optional padding (2) | 02             |
| `hour24(pad)`     | Hour in 24-hour format (00–23)         | 14             |
| `hour12(pad)`     | Hour in 12-hour format (01–12)         | 02             |
| `minute(pad)`     | Minute with optional padding (2)       | 45             |
| `second(pad)`     | Second with optional padding (2)       | 09             |
| `amPm(upper)`     | AM/PM marker (uppercase or lowercase)  | PM             |
| `char(c)`         | Literal character                      | /              |
| `monthName()`     | Persian month name                     | مهر            |
| `dayOfWeekName()` | Persian weekday name                   | سه‌شنبه        |


## License

**MIT License**

Copyright (c) 2024 Farid Solgi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.



## References

* [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
* [Kotlinx datetime](https://github.com/Kotlin/kotlinx-datetime)
