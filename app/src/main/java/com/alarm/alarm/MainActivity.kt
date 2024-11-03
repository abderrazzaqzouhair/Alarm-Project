package com.alarm.alarm

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.ToolBar)
        setSupportActionBar(toolBar)


        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var alarmList = mutableListOf(
            Alarm("Alarm 1", "07:30", true, listOf("Mon", "Tue", "Wed")),
            Alarm("Alarm 1", "12:00", false, listOf("Mon", "Tue", "Wed")),
            Alarm("Alarm 1", "03:50", true, listOf("Mon", "Tue", "Wed")),
        )
        var alarmAdapter = AlarmAdapter(this, alarmList)
        recyclerView.adapter = alarmAdapter

        var fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            var timePickerAlert = TimePickerDialog(this, { _, hour, minute ->
                var time = String.format("%02d:%02d", hour, minute)
                var newAlarm = Alarm("Alarm ", time, true, listOf("Mon", "Tue", "Wed"))
                alarmAdapter.addAlarm(newAlarm)
            }, 0, 0, true)
            timePickerAlert.show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}