package com.example.task.ui.home.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.HomeViewModelFractory
import java.util.*

class EditTaskDialogFragment(var title:String,var description:String,var date:String,var time :String) : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
    private val factory = HomeViewModelFractory()
    private var model : HomeViewModel? = null
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var dateAsString = ""
    private var timeAsString = ""
    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_edit_task_dialog,container,false)

        val editDateButton = rootView.findViewById<Button>(R.id.edit_date_btn)
        editDateButton.text = date
        editDateButton.isEnabled = false
        val editTimeButton = rootView.findViewById<Button>(R.id.edit_time_btn)
        editTimeButton.text = time
        editTimeButton.isEnabled = false
        val editSaveButton = rootView.findViewById<Button>(R.id.edit_save_btn)
        val titleEditText = rootView.findViewById<EditText>(R.id.edit_title_et)
        titleEditText.isEnabled = false
        titleEditText.setText(title)
        val descriptionEditText = rootView.findViewById<EditText>(R.id.edit_description_et)
        descriptionEditText.isEnabled = false
        descriptionEditText.setText(description)
        val editButton = rootView.findViewById<Button>(R.id.edit_btn)
        val deleteButton = rootView.findViewById<Button>(R.id.delete_btn)


        deleteButton.setOnClickListener {

        }

        editSaveButton.setOnClickListener {

        }

        editButton.setOnClickListener {
            titleEditText.isEnabled = true
            descriptionEditText.isEnabled = true
            editDateButton.isEnabled = true
            editTimeButton.isEnabled = true
        }

        editDateButton.setOnClickListener {
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }
        editTimeButton.setOnClickListener {
            TimePickerDialog(requireContext(),this,hour,minute,false).show()
        }

        getDateTimeCalendar()
        return rootView
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
        dateAsString = "$year-$day-$month"

        val savedHourAsString = if (hour<10) "0$hour" else "$hour"
        val savedMinuteAsString = if (minute <10)"0$minute " else "$minute "

        timeAsString = "$savedHourAsString:$savedMinuteAsString"

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
    }
}