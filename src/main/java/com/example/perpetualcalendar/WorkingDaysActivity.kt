package com.example.perpetualcalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import java.time.LocalDate

class WorkingDaysActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_days)

        var startDate: LocalDate = LocalDate.now()
        var endDate: LocalDate = LocalDate.now()
        val startDatePicker: DatePicker = findViewById(R.id.startDatePickerId)
        val endDatePicker: DatePicker = findViewById(R.id.endDatePickerId)

        startDatePicker.setOnDateChangedListener {_, year, monthOfYear, day ->
            val month = monthOfYear + 1
            startDate = LocalDate.of(year, month, day)
            calculateWorkingDays(startDate, endDate)
        }

        endDatePicker.setOnDateChangedListener {_, year, monthOfYear, day ->
            val month = monthOfYear + 1
            endDate = LocalDate.of(year, month, day)
            calculateWorkingDays(startDate, endDate)
        }
    }

    private fun calculateWorkingDays(startDate: LocalDate, endDate: LocalDate) {
        val textView: TextView = findViewById(R.id.calendarDaysTextId)

        if (endDate.isBefore(startDate)) {
            textView.text = "Data końcowa musi być późniejsza niż data początkowa!"
        }
    }
}