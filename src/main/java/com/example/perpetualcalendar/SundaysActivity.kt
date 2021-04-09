package com.example.perpetualcalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.TemporalAdjusters.previous

class SundaysActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private val formatter = MainActivity().formatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sundays)

        val extras = intent.extras ?: return
        val year = extras.getInt(YEAR_MESSAGE)
        val yearMessage = year.toString()

        val textView: TextView = findViewById(R.id.textId)
        listView = findViewById(R.id.listId)

        textView.text = "Niedziele handlowe w $yearMessage roku:"

        if  (year < 2020) {
            displayNoDataMessage()
        } else {
            displaySundaysDates(year)
        }

        val button: Button = findViewById(R.id.okButtonId)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayNoDataMessage() {
        val listItems = arrayOf("Brak danych sprzed roku 2020!")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }

    private fun displaySundaysDates(year: Int) {
        val listItems = calculateSundaysDates(year)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }

    private fun calculateSundaysDates(year: Int): ArrayList<String> {
        val listItems: ArrayList<String> = ArrayList()

        var sundayJanuary = LocalDate.of(year, Month.JANUARY, 31)
        if (sundayJanuary.dayOfWeek != DayOfWeek.SUNDAY) {
            sundayJanuary = sundayJanuary.with(previous(DayOfWeek.SUNDAY))
        }
        var sundayApril = LocalDate.of(year, Month.APRIL, 30)
        if (sundayApril.dayOfWeek != DayOfWeek.SUNDAY) {
            sundayApril = sundayApril.with(previous(DayOfWeek.SUNDAY))
        }

        var sundayJune = LocalDate.of(year, Month.JUNE, 30)
        if (sundayJune.dayOfWeek != DayOfWeek.SUNDAY) {
            sundayJune = sundayJune.with(previous(DayOfWeek.SUNDAY))
        }

        var sundayAugust = LocalDate.of(year, Month.AUGUST, 31)
        if (sundayAugust.dayOfWeek != DayOfWeek.SUNDAY) {
            sundayAugust = sundayAugust.with(previous(DayOfWeek.SUNDAY))
        }

        val sundayEaster = MainActivity().calculateEasterDate(year).minusDays(7)

        val sundayChristmasSecond = LocalDate.of(year, Month.DECEMBER, 25)
                .with(previous(DayOfWeek.SUNDAY))

        val sundayChristmasFirst = sundayChristmasSecond.minusDays(7)

        listItems.add(sundayJanuary.format(formatter))
        listItems.add(sundayEaster.format(formatter))
        listItems.add(sundayApril.format(formatter))
        listItems.add(sundayJune.format(formatter))
        listItems.add(sundayAugust.format(formatter))
        listItems.add(sundayChristmasFirst.format(formatter))
        listItems.add(sundayChristmasSecond.format(formatter))

        return listItems
    }
}