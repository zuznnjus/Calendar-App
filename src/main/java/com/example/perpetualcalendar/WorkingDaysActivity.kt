package com.example.perpetualcalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.ChronoUnit

class WorkingDaysActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_days)

        var startDate: LocalDate = LocalDate.now()
        var endDate: LocalDate = LocalDate.now()
        val startDatePicker: DatePicker = findViewById(R.id.startDatePickerId)
        val endDatePicker: DatePicker = findViewById(R.id.endDatePickerId)
        displayDatesDifference(startDate, endDate)

        startDatePicker.setOnDateChangedListener {_, year, monthOfYear, day ->
            val month = monthOfYear + 1
            startDate = LocalDate.of(year, month, day)
            displayDatesDifference(startDate, endDate)
        }

        endDatePicker.setOnDateChangedListener {_, year, monthOfYear, day ->
            val month = monthOfYear + 1
            endDate = LocalDate.of(year, month, day)
            displayDatesDifference(startDate, endDate)
        }

        val button: Button = findViewById(R.id.buttonId)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayDatesDifference(startDate: LocalDate, endDate: LocalDate) {
        val calendarDaysTextView: TextView = findViewById(R.id.calendarDaysTextId)
        val workingDaysTextView: TextView = findViewById(R.id.workingDaysTextId)

        if (endDate.isBefore(startDate)) {
            calendarDaysTextView.text = "Data końcowa musi być późniejsza"
            workingDaysTextView.text = "niż data początkowa!"
        } else {
            val calendarDays = startDate.until(endDate, ChronoUnit.DAYS)
            calendarDaysTextView.text = "Dni kalendarzowe: $calendarDays"

            val workingDays = calendarDays - calculateDaysOff(startDate, endDate)
            workingDaysTextView.text = "Dni robocze: $workingDays"
        }
    }

    private fun calculateDaysOff(startDate: LocalDate, endDate: LocalDate): Int {
        val holidays : MutableList<Any> = mutableListOf()

        for (year in startDate.year .. endDate.year) {
            holidays.addAll(createHolidaysList(year))
        }

        var date : LocalDate = startDate
        var counter : Int = 0

        while (date.isBefore(endDate)) {
            if (date.dayOfWeek == DayOfWeek.SATURDAY
                || date.dayOfWeek == DayOfWeek.SUNDAY
                || holidays.contains(date)) {
                counter++
            }
            date = date.plusDays(1)
        }

        return counter
    }

    private fun createHolidaysList(year: Int): MutableList<Any> {
        val easter = calculateEasterDate(year)

        return mutableListOf(
            LocalDate.of(year, Month.JANUARY, 1),
            LocalDate.of(year, Month.JANUARY, 6),
            LocalDate.of(year, Month.MAY, 1),
            LocalDate.of(year, Month.MAY, 3),
            LocalDate.of(year, Month.AUGUST, 15),
            LocalDate.of(year, Month.NOVEMBER, 1),
            LocalDate.of(year, Month.DECEMBER, 25),
            LocalDate.of(year, Month.DECEMBER, 26),
            easter.plusDays(1),
            easter.plusDays(60)
        )
    }
}