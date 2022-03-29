package com.example.task.ui.home.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.HomeViewModelFractory
import java.util.*
import kotlin.concurrent.thread


class SaveTaskDialogFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private val factory = HomeViewModelFractory()
    private var model: HomeViewModel? = null
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView: View = inflater.inflate(R.layout.fragment_save_task_dialog, container, false)
        val cancelButton = rootView.findViewById<Button>(R.id.cancel_btn)
        val saveButton = rootView.findViewById<Button>(R.id.save_btn)
        val datePickerButton = rootView.findViewById<Button>(R.id.date_btn)
        val timePickerButton = rootView.findViewById<Button>(R.id.time_btn)

        cancelButton.setOnClickListener {
            dismiss()
        }

        saveButton.setOnClickListener {

            val title = rootView.findViewById<EditText>(R.id.title_et).text.toString()
            val description = rootView.findViewById<EditText>(R.id.description_et).text.toString()
            val done = rootView.findViewById<RadioButton>(R.id.done_rb)
            val todo = rootView.findViewById<RadioButton>(R.id.todo_rb)
            val doing = rootView.findViewById<RadioButton>(R.id.doing_rb)
            if (!(done.isChecked || doing.isChecked || todo.isChecked))
                Toast.makeText(requireContext(), "Please Select A State.", Toast.LENGTH_SHORT)
                    .show()
            else {
                val state =
                    if (done.isChecked) State.DONE else if (todo.isChecked) State.TODO else State.DOING

                if (title.length < 4 || description.length < 4)
                    Toast.makeText(context, "Please fill all the fields.", Toast.LENGTH_SHORT)
                        .show()
                else {
                    val task = Task(
                        0,
                        model!!.username.value,
                        title,
                        description,
                        dateAsString,
                        timeAsString,
                        state,
                        ""
                    )
                    thread { model!!.insertTask(task) }
                    dismiss()
                }
            }

        }

        datePickerButton.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        timePickerButton.setOnClickListener {
            TimePickerDialog(requireContext(), this, hour, minute, false).show()
        }

        getDateTimeCalendar()
        return rootView
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
        dateAsString = "$year-$day-$month"

        val savedHourAsString = if (hour < 10) "0$hour" else "$hour"
        val savedMinuteAsString = if (minute < 10) "0$minute " else "$minute "

        timeAsString = "$savedHourAsString:$savedMinuteAsString"

    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        dateAsString = "$savedYear-$savedDay-$savedMonth"
        getDateTimeCalendar()
        Toast.makeText(requireContext(), dateAsString, Toast.LENGTH_SHORT).show()

    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour = p1
        savedMinute = p2

        val savedHourAsString = if (savedHour < 10) "0$savedHour" else "$savedHour"
        val savedMinuteAsString = if (savedMinute < 10) "0$savedMinute " else "$savedMinute "

        timeAsString = "$savedHourAsString:$savedMinuteAsString"
        getDateTimeCalendar()
        Toast.makeText(requireContext(), timeAsString, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }

}