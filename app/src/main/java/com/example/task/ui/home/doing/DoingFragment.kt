package com.example.task.ui.home.doing

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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


class DoingFragment : Fragment(R.layout.fragment_doing) {
    val factory = HomeViewModelFractory()
    var viewModel : HomeViewModel? = null
    private val taskList = mutableListOf<Task>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerAdapter = TaskRecyclerAdapter(taskList,requireActivity().supportFragmentManager)
        val doingRecyclerView = view.findViewById<RecyclerView>(R.id.doing_recycler)
        doingRecyclerView.adapter = recyclerAdapter
        doingRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel!!.taskList.observe(viewLifecycleOwner){ taskList->
            val doingList = taskList.filter { it.state == State.DOING  }
            taskList.clear()
            taskList.addAll(doingList)
            val emptyTextView : TextView = view.findViewById(R.id.empty_doing_tv)
            if (doingList.isEmpty()){
                emptyTextView.visibility = View.VISIBLE
                doingRecyclerView.visibility = View.GONE
            }
            else{
                emptyTextView.visibility = View.GONE
                doingRecyclerView.visibility = View.VISIBLE
                recyclerAdapter.notifyDataSetChanged()
            }
        }

        thread {
            viewModel!!.getTasks()
        }
        Toast.makeText(requireContext(), viewModel!!.username.value, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
        viewModel!!.username.value = requireActivity().intent.getStringExtra(EXTRAS_USERNAME)
    }
}