# PersianDateMultiplatform

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

PersianDateMultiplatform is a **Kotlin Multiplatform library** for working with the Persian (Jalali/Shamsi) calendar across Android, iOS, Desktop (JVM), and Web (Kotlin/Wasm). The library provides utilities for conversion, formatting, and manipulation of Persian dates, with support for leap years, month and weekday names, and integration into Compose Multiplatform projects.

---

## Features

- **Persian Date Conversion:** Convert between Gregorian and Persian date representations.
- **Date Formatting:** Format Persian date/time objects to strings (date, time, date-time).
- **Leap Year Calculations:** Accurately determine leap years in the Jalali calendar.
- **Month/Weekday Names:** Get Persian month and weekday names.
- **Multiplatform Support:** Use in Android, iOS, Desktop (JVM), and Web (Kotlin/Wasm).
- **DSL for Formatting:** Easy-to-use builder for custom date/time formats.
- **Integration Samples:** Compose Multiplatform project structure for real-world apps.

---

## Getting Started

### 1. Add Dependency

This project is structured as a Kotlin Multiplatform library. To use it in your own KMP project, add the dependency in your shared module:

```kotlin
dependencies {
    implementation("com.faridsolgi:persiandatemultiplatform:<version>")
}
```

Or clone and include the `library` module directly.

### 2. Project Structure

- `/library`: Main multiplatform library code.
- `/composeApp`: Shared code for Compose Multiplatform apps.
- `/iosApp`: iOS entry point and SwiftUI integration.
- Platform-specific implementations are in:
  - `library/src/androidMain`
  - `library/src/iosMain`
  - `library/src/desktopMain`
  - `library/src/wasmJsMain`

---

## Usage

### Basic Persian Date Representation

```kotlin
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime

val persianDate = PersianDateTime(year = 1402, month = 7, day = 1)
```

### Conversion: Gregorian to Persian

```kotlin
import kotlinx.datetime.LocalDate
import com.faridsolgi.persiandatemultiplatform.converter.PersianDateConverter

val gregorianDate = LocalDate(2023, 9, 24)
val persianDate = PersianDateConverter.toPersianDate(gregorianDate)
println(persianDate) // PersianDateTime(year=1402, month=7, day=2, ...)
```

### Formatting

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.format

val formatted = persianDate.format {
    year()
    month()
    day()
}
println(formatted) // "1402 07 02"
```

Or using helpers:

```kotlin
persianDate.toDateString()      // "1402/07/02"
persianDate.toTimeString()      // "00:00:00"
persianDate.toDateTimeString()  // "1402/07/02 00:00:00"
```

### Month and Weekday Names

```kotlin
persianDate.monthName()      // "مهر"
persianDate.dayOfWeekName()  // e.g., "دوشنبه"
```

### Leap Year & Month Length

```kotlin
persianDate.isLeap()         // true/false
persianDate.monthLength()    // 31, 30, or 29
```

### Multiplatform Platform Detection

```kotlin
import com.faridsolgi.persiandatemultiplatform.getPlatform

val platform = getPlatform()
println(platform.name) // "Android 33", "Java 17", "Web with Kotlin/Wasm", etc.
```

---

## Example: Compose Multiplatform Usage

Open the web application in development mode:

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

For iOS, use `/iosApp` as the entry point and integrate with SwiftUI (`ContentView.swift`).

---

## MIT License

```
MIT License

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
```

---

## Contributing & Feedback

Feel free to open issues or PRs! For feedback on Kotlin Multiplatform and Compose/Web, join the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).

If you face any issues, please report them on [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).

---

## References

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/)
- [Kotlin/Wasm](https://kotl.in/wasm/)