package com.example.perpetualcalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val YEAR_MESSAGE = "YEAR"

class MainActivity : AppCompatActivity() {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val yearPicker: NumberPicker = findViewById(R.id.yearPickerId)
        yearPicker.minValue = 1900
        yearPicker.maxValue = 2200
        yearPicker.wrapSelectorWheel = true
        yearPicker.value = LocalDate.now().year

        yearPicker.setOnValueChangedListener {_, _, newValue ->
            val easterDate = calculateEasterDate(newValue)
            calculateAshWednesdayDate(easterDate)
            calculateCorpusChristiDate(easterDate)
            calculateAdventDate(newValue)

            true
        }

        val buttonSundays : Button = findViewById(R.id.buttonSundaysId)
        buttonSundays.setOnClickListener {
            showSundaysActivity(yearPicker.value)
        }

        val buttonWorkingDays : Button = findViewById(R.id.buttonWorkingDaysId)
        buttonWorkingDays.setOnClickListener {
            showWorkingDaysActivity()
        }
    }

    fun calculateEasterDate(selectedYear: Int): LocalDate {
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

    private fun calculateAdventDate(selectedYear: Int): LocalDate {
        val adventText: TextView = findViewById(R.id.adventId)
        var adventDate = LocalDate.of(selectedYear, 12, 25)
        val christmasDayOfWeek = adventDate.dayOfWeek

        for (i in 1..7) {
            if (christmasDayOfWeek.minus(i.toLong()) == DayOfWeek.SUNDAY) {
                adventDate = adventDate.minusDays((3 * 7 + i).toLong())
                break
            }
        }

        val formattedAdventDate = adventDate.format(formatter)
        adventText.text = "Adwent: $formattedAdventDate"

        return adventDate
    }

    private fun showSundaysActivity(selectedYear: Int) {
        val intent = Intent(this, SundaysActivity::class.java)
        intent.putExtra(YEAR_MESSAGE, selectedYear)
        startActivity(intent)
    }

    private fun showWorkingDaysActivity() {
        val intent = Intent(this, WorkingDaysActivity::class.java)
        startActivity(intent)
    }
}