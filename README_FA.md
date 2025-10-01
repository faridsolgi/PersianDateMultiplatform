# پرشین‌دیت چندسکویی

[![لایسنس: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Compose-Multiplatform-blueviolet.svg)](https://kotlinlang.org/lp/multiplatform/)
![اندروید](https://img.shields.io/badge/Android-✔-green.svg)
![iOS](https://img.shields.io/badge/iOS-✔-blue.svg)
![JVM](https://img.shields.io/badge/JVM-✔-orange.svg)
![WASM](https://img.shields.io/badge/WASM-✔-purple.svg)

**پرشین‌دیت چندسکویی** یک کتابخانه Kotlin Multiplatform برای کار با تقویم فارسی (جلالی/شمسی) در اندروید، iOS، دسکتاپ (JVM) و وب (Kotlin/Wasm) است. این کتابخانه امکانات متنوعی برای تبدیل، قالب‌بندی و مدیریت تاریخ شمسی ارائه می‌دهد.

<img width="1920" height="1080" alt="kotlin_multiplatform_persian_date_time" src="https://github.com/user-attachments/assets/1b412ce5-85c5-4f74-b416-1f50b25f80a1" />

## امکانات

* **تبدیل تاریخ فارسی**: تبدیل بین تاریخ میلادی و شمسی
* **قالب‌بندی تاریخ**: تبدیل تاریخ/زمان شمسی به رشته (تاریخ، زمان، تاریخ‌زمان)
* **محاسبات سال کبیسه**: تشخیص دقیق سال‌های کبیسه در تقویم جلالی
* **نام ماه/روز هفته**: دریافت نام ماه و روز هفته شمسی
* **پشتیبانی چندسکویی**: قابل استفاده در اندروید، iOS، دسکتاپ و وب
* **DSL برای قالب‌بندی**: ساخت قالب‌های سفارشی تاریخ/زمان به راحتی
* **نمونه‌های یکپارچه‌سازی**: پروژه نمونه Compose Multiplatform برای اپلیکیشن‌های واقعی

## شروع به کار

### پروژه‌های چندسکویی Kotlin (commonMain)

![نسخه](https://img.shields.io/badge/version-0.2.0--beta1-green.svg)

این کتابخانه در Maven Central منتشر شده است و با Kotlin Standard Library نسخه ۲.۱.۲۰ یا بالاتر سازگار است.

در صورت هدف‌گیری اندروید با API پایین‌تر از ۲۶، باید Android Gradle plugin نسخه ۴ یا بالاتر و core library desugaring را فعال کنید.

⚠️ توجه: این کتابخانه به آخرین نسخه [Kotlinx datetime](https://github.com/Kotlin/kotlinx-datetime) وابسته است.

افزودن وابستگی به `commonMain` در `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                // افزودن kotlinx-datetime ابتدا
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:<version>")
                // سپس کتابخانه PersianDateTime
                implementation("io.github.faridsolgi:persianDateTime:<version>")
            }
        }
    }
}
```

### پروژه‌های اندروید نیتیو

استفاده از نسخه اندروید اختصاصی:

```kotlin
dependencies {
    // افزودن kotlinx-datetime ابتدا
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:<version>")
    // سپس نسخه اندروید PersianDateTime
    implementation("io.github.faridsolgi:persianDateTime:<version>")
}
```

دریافت کتابخانه از Maven Central: [PersianDateTime on Maven Central](https://central.sonatype.com/namespace/io.github.faridsolgi) یا کلون و افزودن مستقیم ماژول کتابخانه.

### پشتیبانی از Kotlinx Serialization

در صورت افزودن [kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization)، PersianDateTime از `@Serializable` پشتیبانی می‌کند.

## استفاده

### ایجاد تاریخ فارسی ساده

```kotlin
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
// ایجاد تاریخ فقط
val persianDate = PersianDateTime(year = 1402, month = 7, day = 1)
// ایجاد تاریخ با زمان
val persianDateTime = PersianDateTime(
    year = 1402,
    month = 7,
    day = 1,
    hour = 14,
    minute = 30,
    second = 45
)
```

### پارس کردن تاریخ فارسی از رشته

```kotlin
// پارس فقط تاریخ
val parsedDate = PersianDateTime.parse("1402/07/01")
// پارس تاریخ با زمان
val parsedDateTime = PersianDateTime.parse("1402-07-01 14:30:45")
```

### تبدیل از timestamp

```kotlin
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
import kotlinx.datetime.TimeZone
// تبدیل از epoch milliseconds
val timestamp = 1759323028800L
val persianDateTime = PersianDateTime.parse(timestamp, TimeZone.currentSystemDefault())

println(persianDateTime.year)   // 1404
println(persianDateTime.month)  // 7
println(persianDateTime.day)    // 9
println(persianDateTime.hour)   // 16
```

### مثال‌های بیشتر

```kotlin
// دریافت اجزای تاریخ
println(parsedDate.year)   // 1402
println(parsedDate.month)  // 7
println(parsedDate.day)    // 1
// قالب‌بندی تاریخ فارسی به رشته
val formatted = parsedDateTime.toString()
println(formatted) // مثال: "1402-07-01 14:30:45"
```

### استفاده از توابع افزونه

#### تبدیل تاریخ میلادی به فارسی

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.toPersianDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
val gregorianDate = LocalDate(2023, 9, 24)
val persianDate = gregorianDate.toPersianDateTime() // PersianDateTime
val gregorianDateTime = LocalDateTime(2023, 9, 24, 15, 20, 0)
val persianDateTime = gregorianDateTime.toPersianDateTime()
```

#### تبدیل تاریخ فارسی به میلادی

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.toGregorian
val gregorian = persianDate.toLocalDate()
val gregorianWithTime = persianDate.toLocalDateTime()
```

#### دریافت تاریخ فعلی

```kotlin
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import com.faridsolgi.persiandatemultiplatform.converter.nowInTehran
import com.faridsolgi.persiandatemultiplatform.converter.nowPersianDate
// تاریخ فعلی در منطقه زمانی تهران
val nowInTehran = Clock.System.nowInTehran
println("تاریخ شمسی فعلی تهران: \\$\{nowInTehran.toDateString()}")
// تاریخ فعلی برای منطقه زمانی دیگر
val nowInParis = Clock.System.nowPersianDate(TimeZone.of("Europe/Paris"))
println("تاریخ شمسی فعلی پاریس: \\$\{nowInParis.toDateString()}")
```

#### عملیات ریاضی روی تاریخ

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.plusDays
import com.faridsolgi.persiandatemultiplatform.converter.minusDays
val nextWeek = persianDate.plusDays(7)
val yesterday = persianDate.minusDays(1)
```

#### مقایسه تاریخ‌ها

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.isBefore
import com.faridsolgi.persiandatemultiplatform.converter.isAfter
import com.faridsolgi.persiandatemultiplatform.converter.isBetween
if (persianDate.isBefore(nextWeek)) { /* ... */ }
if (persianDate.isAfter(yesterday)) { /* ... */ }
if (persianDate.isBetween(yesterday, nextWeek)) { /* ... */ }
```

#### ماه، روز، کبیسه

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.isLeap
import com.faridsolgi.persiandatemultiplatform.converter.monthLength
import com.faridsolgi.persiandatemultiplatform.converter.monthName
import com.faridsolgi.persiandatemultiplatform.converter.dayOfWeekName
println(persianDate.isLeap())        // درست/غلط
println(persianDate.monthLength())   // ۳۱، ۳۰ یا ۲۹
println(persianDate.persianMonth().displayName)     // "مهر"
println(persianDate.persianDayOfWeek().displayName) // "سه‌شنبه"
```

#### قالب‌بندی رشته تاریخ

```kotlin
import com.faridsolgi.persiandatemultiplatform.converter.toDateString
import com.faridsolgi.persiandatemultiplatform.converter.toTimeString
import com.faridsolgi.persiandatemultiplatform.converter.toDateTimeString
println(persianDate.toDateString())      // "1402/07/02"
println(persianDate.toTimeString())      // "00:00:00"
println(persianDate.toDateTimeString())  // "1402/07/02 00:00:00"
```

#### قالب‌بندی سفارشی DSL

DSL قالب‌بندی از الگوهای کامل تاریخ/زمان پشتیبانی می‌کند:

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
println(custom) // "02/07/1402 03:45ب.‌ظ"
```

### مرجع DSL قالب‌بندی

| تابع              | توضیح                                 | خروجی نمونه   |
|-------------------|---------------------------------------|---------------|
| `year(pad)`       | سال با پدینگ اختیاری (پیش‌فرض ۴)      | 1402          |
| `month(pad)`      | شماره ماه با پدینگ اختیاری (۲)         | 07            |
| `day(pad)`        | روز ماه با پدینگ اختیاری (۲)           | 02            |
| `hour24(pad)`     | ساعت ۲۴ ساعته (۰۰–۲۳)                  | 14            |
| `hour12(pad)`     | ساعت ۱۲ ساعته (۰۱–۱۲)                  | 02            |
| `minute(pad)`     | دقیقه با پدینگ اختیاری (۲)              | 45            |
| `second(pad)`     | ثانیه با پدینگ اختیاری (۲)              | 09            |
| `amPm()`          | نشانگر ب.‌ظ/ق.‌ظ                       | ب.‌ظ، ق.‌ظ     |
| `char(c)`         | کاراکتر ثابت                           | /             |
| `monthName()`     | نام ماه شمسی                            | مهر           |
| `dayOfWeekName()` | نام روز هفته شمسی                       | سه‌شنبه       |

## اعتبارسنجی و خطاها

تمام نمونه‌های `PersianDateTime` به طور خودکار اعتبارسنجی می‌شوند و اگر تاریخ یا زمان نامعتبر وارد کنید، خطای **`IllegalArgumentException`** رخ می‌دهد. نیاز به فراخوانی اعتبارسنج وجود ندارد و داخلی انجام می‌شود.

### قواعد بررسی شده
* **ماه**: بین ۱ تا ۱۲
* **روز**: معتبر بودن نسبت به ماه و سال (شامل کبیسه برای ماه ۱۲)
* **زمان**: ساعت ۰–۲۳، دقیقه ۰–۵۹، ثانیه ۰–۵۹

### مثال استفاده

```kotlin
import com.faridsolgi.persiandatemultiplatform.domain.PersianDateTime
// ✅ تاریخ معتبر
val validDate = PersianDateTime(1402, 7, 15)
// ❌ ماه نامعتبر
try {
    PersianDateTime(1402, 13, 5)
} catch (e: IllegalArgumentException) {
    println(e.message) // "ماه نامعتبر: 13"
}
// ❌ روز نامعتبر
try {
    PersianDateTime(1402, 7, 32)
} catch (e: IllegalArgumentException) {
    println(e.message) // "روز نامعتبر: 32 برای ماه 7"
}
// ❌ ساعت نامعتبر
try {
    PersianDateTime(1402, 7, 15, 25, 0, 0)
} catch (e: IllegalArgumentException) {
    println(e.message) // "ساعت نامعتبر: 25"
}
// ✅ پارس رشته تاریخ
val parsedDate = PersianDateTime.parse("1402/07/01 14:30:45")
// ❌ پارس رشته نامعتبر
try {
    PersianDateTime.parse("1402/13/01")
} catch (e: IllegalArgumentException) {
    println(e.message) // "ماه نامعتبر: 13"
}
```

> ⚠️ **نکته:** هنگام دریافت ورودی کاربر یا پارس رشته، همیشه خطای `IllegalArgumentException` را مدیریت کنید تا تاریخ‌های نامعتبر باعث اختلال نشوند.

## مجوز

**مجوز MIT**

Copyright (c) 2024 Farid Solgi

اجازه داده می‌شود که هر فردی یک نسخه رایگان از نرم‌افزار و مستندات مرتبط را دریافت و استفاده کند، و بدون هیچ محدودیتی از جمله: حق استفاده، کپی، تغییر، ادغام، نشر، توزیع، زیرمجوز و فروش نسخه‌های نرم‌افزار و اجازه دادن به افراد دیگر برای این اعمال، مشروط بر درج اطلاعیه کپی‌رایت بالا و این مجوز در تمام نسخه‌ها یا بخش‌های قابل توجه نرم‌افزار.

این نرم‌افزار "همان‌طور که هست" ارائه می‌شود، بدون هیچ‌گونه ضمانت، اعم از صریح یا ضمنی، از جمله اما نه محدود به ضمانت‌های قابلیت فروش، تناسب برای هدف خاص و عدم نقض حقوق. در هیچ حالتی نویسندگان یا دارندگان کپی‌رایت مسئول هیچ گونه خسارت، ادعا، یا مسئولیتی نخواهند بود، چه در قرارداد، چه در مسئولیت مدنی یا غیره، که ناشی از نرم‌افزار یا استفاده یا دیگر ارتباطات با نرم‌افزار باشد.

## منابع
* [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
* [Kotlinx datetime](https://github.com/Kotlin/kotlinx-datetime)
