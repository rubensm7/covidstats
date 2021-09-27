package com.ruben.covidstats.ui.view.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.ruben.covidstats.R
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(private val currentDate: String,private val dateFrom: String, val listener: (day: Int, month: Int, year: Int) -> Unit) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        if (currentDate.isNotEmpty()) c.time = simpleDateFormat.parse(currentDate)
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val picker = DatePickerDialog(activity as Context, R.style.DatePickerTheme, this, year, month, day)
        picker.datePicker.maxDate = Date().time
        if (dateFrom.isNotEmpty()){
            picker.datePicker.minDate = simpleDateFormat.parse(dateFrom).time
        }
        return picker
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day,month+1,year)
    }


}