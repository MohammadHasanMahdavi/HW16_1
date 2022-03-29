package com.example.task.ui.home.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class EditTaskDialogFragment(var title:String,var description:String,var date:String,var time :String,var taskId:Int) : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
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
        val editSaveButton = rootView.findViewById<Button>(R.id.edit_save_btn2)
        val titleEditText = rootView.findViewById<EditText>(R.id.edit_title_et)
        titleEditText.isEnabled = false
        titleEditText.setText(title)
        val descriptionEditText = rootView.findViewById<EditText>(R.id.edit_description_et)
        descriptionEditText.isEnabled = false
        descriptionEditText.setText(description)
        val editButton = rootView.findViewById<Button>(R.id.edit_btn)
        val deleteButton = rootView.findViewById<Button>(R.id.delete_btn)
        val shareButton = rootView.findViewById<Button>(R.id.edit_share_btn)
        val todoRadioButton = rootView.findViewById<RadioButton>(R.id.edit_todo_state)
        val doneRadioButton = rootView.findViewById<RadioButton>(R.id.edit_done_state)
        val doingRadioButton = rootView.findViewById<RadioButton>(R.id.edit_doing_state)



        shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "title : $title\ndescription : $description\ndate : $date\ntime : $time\n")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        deleteButton.setOnClickListener {
            model!!.deleteTask(
                Task(taskId,model!!.username.value,title,description,date,time,State.DONE,"")
            )
            dismiss()
        }

        editSaveButton.setOnClickListener {
            val state = if (todoRadioButton.isChecked) State.TODO else if (doneRadioButton.isChecked) State.DONE else State.DOING
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            model!!.updateTask(
                Task(taskId,model!!.username.value,title,description,dateAsString,timeAsString,state,"")
            )
            Log.d("TAGGG",taskId.toString())
            Toast.makeText(requireContext(), taskId.toString(), Toast.LENGTH_SHORT).show()
            dismiss()
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