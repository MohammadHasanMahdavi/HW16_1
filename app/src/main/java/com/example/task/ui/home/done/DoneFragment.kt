package com.example.task.ui.home.done

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.HomeViewModelFractory
import com.example.task.ui.home.TaskRecyclerAdapter
import com.example.task.ui.login.EXTRAS_USERNAME
import kotlin.concurrent.thread


class DoneFragment : Fragment(R.layout.fragment_done) {
    val factory = HomeViewModelFractory()
    var model : HomeViewModel? = null
    val taskList = mutableListOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerAdapter = TaskRecyclerAdapter(taskList,requireActivity().supportFragmentManager)
        val doneRecyclerView = view.findViewById<RecyclerView>(R.id.done_recycler)
        doneRecyclerView.adapter = recyclerAdapter
        doneRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        model!!.taskList.observe(viewLifecycleOwner){
            val doneList = it.filter { it.state == State.DONE }
            taskList.clear()
            taskList.addAll(it.filter { it.state == State.DONE })
            val emptyTextView : TextView = view.findViewById(R.id.empty_done_tv)
            if (doneList.isEmpty()){
                emptyTextView.visibility = View.VISIBLE
                doneRecyclerView.visibility = View.GONE
            }
            else{
                emptyTextView.visibility = View.GONE
                doneRecyclerView.visibility = View.VISIBLE
                recyclerAdapter.notifyDataSetChanged()
            }
        }

        thread {
            model!!.getTasks()
        }
        Toast.makeText(requireContext(), model!!.username.value, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
        model!!.username.value = requireActivity().intent.getStringExtra(EXTRAS_USERNAME)
    }
}