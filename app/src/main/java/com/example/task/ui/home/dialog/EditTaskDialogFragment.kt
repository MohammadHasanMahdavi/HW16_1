package com.example.task.ui.home.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.databinding.FragmentEditTaskDialogBinding
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.HomeViewModelFractory
import java.util.*

class EditTaskDialogFragment(
    var title: String,
    var description: String,
    var date: String,
    var time: String,
    private var taskId: Int
) : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val factory = HomeViewModelFractory()
    private var model: HomeViewModel? = null
    private lateinit var binding: FragmentEditTaskDialogBinding

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
        binding = FragmentEditTaskDialogBinding.inflate(layoutInflater)

        val editDateButton = binding.editDateBtn
        editDateButton.text = date
        editDateButton.isEnabled = false

        val editTimeButton = binding.editTimeBtn
        editTimeButton.text = time
        editTimeButton.isEnabled = false

        val titleEditText = binding.editTitleEt
        titleEditText.isEnabled = false
        titleEditText.setText(title)

        val descriptionEditText = binding.editDescriptionEt
        descriptionEditText.isEnabled = false
        descriptionEditText.setText(description)




        binding.editShareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "title : $title\ndescription : $description\ndate : $date\ntime : $time\n"
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.deleteBtn.setOnClickListener {
            model!!.deleteTask(
                Task(taskId, model!!.username.value, title, description, date, time, State.DONE, "")
            )
            dismiss()
        }

        binding.editSaveBtn2.setOnClickListener {
            val state =
                if (binding.editTodoState.isChecked) State.TODO else if (binding.editDoneState.isChecked) State.DONE else State.DOING
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            model!!.updateTask(
                Task(
                    taskId,
                    model!!.username.value,
                    title,
                    description,
                    dateAsString,
                    timeAsString,
                    state,
                    ""
                )
            )
            Toast.makeText(requireContext(), taskId.toString(), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.editBtn.setOnClickListener {
            titleEditText.isEnabled = true
            descriptionEditText.isEnabled = true
            editDateButton.isEnabled = true
            editTimeButton.isEnabled = true
        }
        editDateButton.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        editTimeButton.setOnClickListener {
            TimePickerDialog(requireContext(), this, hour, minute, false).show()
        }
        getDateTimeCalendar()
        return binding.root
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val savedMonth = month
        val savedYear = year
        dateAsString = "$savedYear-$dayOfMonth-$savedMonth"
        Toast.makeText(requireContext(), dateAsString, Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val savedHourAsString = if (p1 < 10) "0$p1" else "$p1"
        val savedMinuteAsString = if (p2 < 10) "0$p2 " else "$p2 "
        timeAsString = "$savedHourAsString:$savedMinuteAsString"
        Toast.makeText(requireContext(), timeAsString, Toast.LENGTH_SHORT).show()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)
    }
}