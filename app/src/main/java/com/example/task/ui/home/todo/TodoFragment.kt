package com.example.task.ui.home.todo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.FragmentTodoBinding
import com.example.task.model.State
import com.example.task.model.Task
import com.example.task.ui.home.HomeViewModel
import com.example.task.ui.home.TaskRecyclerAdapter
import com.example.task.ui.home.HomeViewModelFractory
import com.example.task.ui.login.EXTRAS_USERNAME
import kotlin.concurrent.thread


class TodoFragment : Fragment(R.layout.fragment_todo){

    private var viewModel : HomeViewModel? = null
    private val taskList = mutableListOf<Task>()
    private lateinit var binding : FragmentTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoBinding.inflate(layoutInflater)
        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerAdapter = TaskRecyclerAdapter(taskList,requireActivity().supportFragmentManager)
        val todoRecyclerView : RecyclerView= binding.todoRecycler
        todoRecyclerView.adapter = recyclerAdapter
        todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())


            viewModel!!.taskList.observe(viewLifecycleOwner){ taskList ->

                val todoList = taskList.filter { it.state == State.TODO }
                taskList.clear()
                taskList.addAll(todoList)

                val emptyTextView : TextView = binding.emptyTodoTv

                if (todoList.isEmpty()){
                    emptyTextView.visibility = View.VISIBLE
                    todoRecyclerView.visibility = View.GONE
                }
                else{
                    emptyTextView.visibility = View.GONE
                    todoRecyclerView.visibility = View.VISIBLE
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
        val factory = HomeViewModelFractory()
        viewModel = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
        viewModel!!.username.value = requireActivity().intent.getStringExtra(EXTRAS_USERNAME)
    }
}


