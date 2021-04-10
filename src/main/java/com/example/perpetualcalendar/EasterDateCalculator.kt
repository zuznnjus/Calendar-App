package com.example.perpetualcalendar

import java.time.LocalDate

fun calculateEasterDate(selectedYear: Int): LocalDate {
    val a: Int = selectedYear % 19
    val b: Int = selectedYear / 100
    val c: Int = selectedYear % 100
    val d: Int = b / 4
    val e: Int = b % 4
    val f: Int = (b + 8) / 25
    val g: Int = (b - f + 1) / 3
    val h: Int = (19 * a + b - d - g + 15) % 30
    val i: Int = c / 4
    val k: Int = c % 4
    val l: Int = (32 + 2 * e + 2 * i - h - k) % 7
    val m: Int = (a + 11 * h + 22 * l) / 451
    val p: Int = (h + l - 7 * m + 114) % 31
    val day: Int = p + 1
    val month: Int = (h + l - 7 * m + 114) / 31

    return LocalDate.of(selectedYear, month, day)
}

