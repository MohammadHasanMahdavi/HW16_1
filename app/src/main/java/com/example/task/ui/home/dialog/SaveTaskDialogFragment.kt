package com.example.task.ui.home.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.databinding.FragmentSaveTaskDialogBinding
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.HomeViewModelFractory
import java.util.*
import kotlin.concurrent.thread


class SaveTaskDialogFragment: DialogFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private val factory = HomeViewModelFractory()
    private var model: HomeViewModel? = null
    private lateinit var binding:FragmentSaveTaskDialogBinding
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var dateAsString = ""
    private var timeAsString = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSaveTaskDialogBinding.inflate(layoutInflater)

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.saveBtn.setOnClickListener {

            val title = binding.titleEt.text.toString()
            val description = binding.descriptionEt.text.toString()
            val done = binding.doneRb
            val todo = binding.todoRb
            val doing = binding.doingRb
            if (!(done.isChecked || doing.isChecked || todo.isChecked)) {
                Toast.makeText(requireContext(), "Please Select A State.", Toast.LENGTH_SHORT)
                    .show()
            } else {
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

        binding.dateBtn.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.timeBtn.setOnClickListener {
            TimePickerDialog(requireContext(), this, hour, minute, false).show()
        }

        getDateTimeCalendar()
        return binding.root
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
        dateAsString = "$year-$dayOfMonth-$month"
        Toast.makeText(requireContext(), dateAsString, Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val savedHourAsString = if (p1 < 10) "0$p1" else "$p1"
        val savedMinuteAsString = if (p2 < 10) "0$p2 " else "$p2 "
        timeAsString = "$savedHourAsString:$savedMinuteAsString"

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }

}