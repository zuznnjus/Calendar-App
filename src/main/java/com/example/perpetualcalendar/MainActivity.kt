package com.example.perpetualcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val yearPicker: NumberPicker = findViewById(R.id.yearPickerId)
        yearPicker.minValue = 1900
        yearPicker.maxValue = 2200
        yearPicker.wrapSelectorWheel = true
        yearPicker.value = 2021

        yearPicker.setOnValueChangedListener() {_, _, newValue ->
            val easterDate = calculateEasterDate(newValue)
            calculateAshWednesdayDate(easterDate)
            calculateCorpusChristiDate(easterDate)
            calculateAdventDate()

            true
        }
    }

    private fun calculateEasterDate(selectedYear: Int): LocalDate {
        val a = selectedYear % 19
        val b = kotlin.math.floor(selectedYear / 100.0)
        val c = selectedYear % 100
        val d = kotlin.math.floor(b / 4)
        val e = b % 4
        val f = kotlin.math.floor((b + 8) / 25)
        val g = kotlin.math.floor((b - f + 1) / 3)
        val h = (19 * a + b - d - g + 15) % 30
        val i = kotlin.math.floor(c / 4.0)
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = kotlin.math.floor((a + 11 * h + 22 * l) / 451)
        val p = (h + l - 7 * m + 114) % 31
        val day = (p + 1).toInt()
        val month = kotlin.math.floor((h + l - 7 * m + 114) / 31).toInt()

        val easterText: TextView = findViewById(R.id.easterId)
        val easterDate = LocalDate.of(selectedYear, month, day).format(formatter)

        easterText.text = "Wielkanoc: $easterDate"

        return LocalDate.of(selectedYear, month, day)
    }

    private fun calculateAshWednesdayDate(easterDate : LocalDate ) {
        val ashWednesdayText: TextView = findViewById(R.id.ashWensdayId)
        val ashWednesdayDate = easterDate.minusDays(46).format(formatter)

        ashWednesdayText.text = "Popielec: $ashWednesdayDate"

    }

    private fun calculateCorpusChristiDate(easterDate : LocalDate ) {
        val corpusChristiText: TextView = findViewById(R.id.corpusChristiId)
        val corpusChristiDate = easterDate.plusDays(60).format(formatter)

        corpusChristiText.text = "Boże Ciało: $corpusChristiDate"
    }

    private fun calculateAdventDate() {
        val adventText: TextView = findViewById(R.id.adventId)
    }
}