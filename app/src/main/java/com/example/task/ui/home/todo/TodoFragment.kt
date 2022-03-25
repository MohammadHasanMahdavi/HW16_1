package com.example.task.ui.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.Task
import com.example.task.ui.MyViewModel
import com.example.task.ui.TaskRecyclerAdapter
import com.example.task.ui.ViewModelFractory
import com.example.task.ui.dialog.EditTaskDialogFragment
import kotlin.concurrent.thread


class TodoFragment : Fragment(R.layout.fragment_todo) {
    val factory = ViewModelFractory()
    var model : MyViewModel? = null
    val taskList = mutableListOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerAdapter = TaskRecyclerAdapter(taskList,requireActivity().supportFragmentManager)
        val todoRecyclerView = view.findViewById<RecyclerView>(R.id.todo_recycler)
        todoRecyclerView.adapter = recyclerAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())


            model!!.taskList.observe(viewLifecycleOwner){
                taskList.clear()
                taskList.addAll(it)
                recyclerAdapter.notifyDataSetChanged()
        }

        thread {  model!!.getTasks()}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(),factory).get(MyViewModel::class.java)
    }
}
