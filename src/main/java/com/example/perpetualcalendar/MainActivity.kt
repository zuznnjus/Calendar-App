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
        displayDates(yearPicker.value)

        yearPicker.setOnValueChangedListener {_, _, newValue ->
            displayDates(newValue)
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

    private fun displayDates(year: Int) {
        val easterDate = calculateEaster(year)
        calculateAshWednesdayDate(easterDate)
        calculateCorpusChristiDate(easterDate)
        calculateAdventDate(year)
    }

    private fun calculateEaster(selectedYear: Int): LocalDate {
        val easterDate = calculateEasterDate(selectedYear)
        val easterText: TextView = findViewById(R.id.easterId)
        val formattedEasterDate = easterDate.format(formatter)

        easterText.text = "Wielkanoc: $formattedEasterDate"
        return easterDate
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