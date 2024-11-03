package com.alarm.alarm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AlarmAdapter(
    var context: Context,
    var alarms: MutableList<Alarm>,
) :
    RecyclerView.Adapter<AlarmAdapter.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var days: List<TextView> = listOf(
            view.findViewById(R.id.S),
            view.findViewById(R.id.M),
            view.findViewById(R.id.Tl),
            view.findViewById(R.id.W),
            view.findViewById(R.id.T),
            view.findViewById(R.id.F),
            view.findViewById(R.id.Su)
        )
        var label: TextView = view.findViewById(R.id.label)
        var vibrate :LinearLayout = view.findViewById(R.id.vibrate)
        var checkVibrate: CheckBox = view.findViewById(R.id.vibrateCh)
        var date = view.findViewById<TextView>(R.id.date)
        var calendar: LinearLayout = view.findViewById(R.id.calendar)
        var option: LinearLayout = view.findViewById(R.id.option)
        var delete: LinearLayout = view.findViewById(R.id.delete)
        var moreOption: ImageView = view.findViewById(R.id.moreOption)
        var time: TextView = view.findViewById(R.id.time)
        var status: TextView = view.findViewById(R.id.status)
        var switch: Switch = view.findViewById(R.id.switch1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return Holder(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        var alarm = alarms[position]
        holder.time.text = alarm.time
        holder.status.text = if (alarm.status) "Active" else "Not Active"
        holder.switch.isChecked = alarm.status
        if (holder.switch.isChecked) {
            holder.switch.thumbTintList = ColorStateList.valueOf(Color.BLUE)
            holder.switch.trackTintList = ColorStateList.valueOf(Color.BLUE)
        } else {
            holder.switch.thumbTintList = ColorStateList.valueOf(Color.GRAY)
            holder.switch.trackTintList = ColorStateList.valueOf(Color.GRAY)
        }
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            alarm.status = isChecked
            holder.status.text = if (isChecked) "Active" else "Not Active"

            Log.d("abderrazzaq", alarms.toString())
            if (isChecked) {
                holder.switch.thumbTintList = ColorStateList.valueOf(Color.BLUE)
                holder.switch.trackTintList = ColorStateList.valueOf(Color.BLUE)
            } else {
                holder.switch.thumbTintList = ColorStateList.valueOf(Color.GRAY)
                holder.switch.trackTintList = ColorStateList.valueOf(Color.GRAY)
            }
        }
        holder.time.setOnClickListener {
            var heur = alarm.time.substring(0, 2).toInt()
            var min = alarm.time.substring(3, 5).toInt()
            var TimePickerAlert = TimePickerDialog(context, { _, hour, minute ->
                alarm.time = String.format("%02d:%02d", hour, minute)
                holder.time.text = alarm.time
                Log.d("abderrazzaq", alarms.toString())
            }, heur, min, true)
            TimePickerAlert.show()
        }
        holder.days.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                if (textView.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#C2DEDE"))) {
                    textView.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#C78686"))
                } else {
                    textView.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#C2DEDE"))
                }
            }
        }


        holder.label.setOnClickListener {
            showInputDialog(holder.label, if(holder.label.text.toString() == "" || holder.label.text.toString() == "add label") " " else holder.label.text.toString())


        }
        holder.moreOption.setOnClickListener {
            if (holder.option.visibility == View.GONE) {
                holder.label.visibility = View.VISIBLE
                holder.label.setPadding(0, 12, 0, 0)
                if(holder.label.text.toString() == ""){
                    holder.label.text = "Add label"
                }
                holder.option.visibility = View.VISIBLE
                holder.moreOption.setImageResource(R.drawable.drop_up)

            } else {
                holder.option.visibility = View.GONE
                holder.label.setPadding(0, 0, 0, 0)
                if(holder.label.text.toString() == "" || holder.label.text.toString() == "add label"){
                    holder.label.visibility = View.GONE
                }
                holder.moreOption.setImageResource(R.drawable.drop_dawn)
            }
        }
        holder.delete.setOnClickListener {
            deleteAlarm(alarm)
        }
        holder.calendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format("%02d %s %d", selectedDay, getMonthName(selectedMonth), selectedYear)
                holder.date.text = selectedDate
            }, 2024, 11, 4)

            datePickerDialog.show()
        }
        holder.vibrate.setOnClickListener {
            if (holder.checkVibrate.isChecked) {
                holder.checkVibrate.isChecked = false
            } else {
                holder.checkVibrate.isChecked = true
            }
        }

        holder.checkVibrate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.checkVibrate.buttonTintList = ColorStateList.valueOf(Color.GREEN) // Change color when checked
            } else {
                holder.checkVibrate.buttonTintList = ColorStateList.valueOf(Color.parseColor("#000000")) // Change color when unchecked
            }
        }
    }

    override fun getItemCount(): Int = alarms.size

    fun addAlarm(alarm: Alarm) {
        alarms.add(alarm)
        notifyItemInserted(alarms.size - 1)
    }

    fun deleteAlarm(alarm: Alarm) {

        val position = alarms.indexOf(alarm)
        if (position != -1) {
            alarms.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    private fun getMonthName(month: Int): String {
        return when (month) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> "Invalid month"
        }
    }
    private fun showInputDialog(label:TextView, value:String){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        val editTextDialogInput = dialogView.findViewById<EditText>(R.id.editTextDialogInput)
        editTextDialogInput.setText(value)
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Add label")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                val inputText = editTextDialogInput.text.toString()
                label.text = inputText
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()
    }


}